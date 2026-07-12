package com.ecommerce.service.impl;

import com.ecommerce.client.cart.response.CartItemResponse;
import com.ecommerce.client.cart.response.CartResponse;
import com.ecommerce.client.catalog.response.ProductResponse;
import com.ecommerce.dto.request.OrderRequest;
import com.ecommerce.dto.request.UpdateOrderStatusRequest;
import com.ecommerce.dto.response.OrderResponse;
import com.ecommerce.dto.response.PageResponse;
import com.ecommerce.exception.BadRequestException;
import com.ecommerce.exception.ResourceNotFoundException;
import com.ecommerce.factory.OrderFactory;
import com.ecommerce.factory.OrderItemFactory;
import com.ecommerce.mapper.OrderMapper;
import com.ecommerce.model.ENUM.OrderStatus;
import com.ecommerce.model.Order;
import com.ecommerce.model.OrderItem;
import com.ecommerce.repository.OrderRepository;
import com.ecommerce.security.UserContext;
import com.ecommerce.service.OrderService;
import com.ecommerce.client.cart.CartClient;
import com.ecommerce.client.catalog.CatalogClient;
import com.ecommerce.client.inventory.InventoryClient;
import com.ecommerce.client.notification.NotificationClient;
import com.ecommerce.client.payment.PaymentClient;
import com.ecommerce.state.*;
import com.ecommerce.util.PageRequestUtil;
import com.ecommerce.util.PageResponseUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class OrderServiceImpl implements OrderService {

    private final OrderRepository orderRepository;
    private final OrderMapper orderMapper;

    private final OrderFactory orderFactory;
    private final OrderItemFactory orderItemFactory;

    private final CartClient cartClient;
    private final CatalogClient catalogClient;
    private final InventoryClient inventoryClient;
    private final PaymentClient paymentClient;
    private final NotificationClient notificationClient;

    private final PendingState pendingState;
    private final ConfirmedState confirmedState;
    private final ShippedState shippedState;
    private final DeliveredState deliveredState;
    private final CancelledState cancelledState;

    @Override
    public OrderResponse createOrder(OrderRequest request) {

        Order order = orderFactory.createOrder(request);

        Long userId = UserContext.getCurrentUserId();

        CartResponse cart = cartClient.getCartByUserId(userId);

        if (cart == null ||
                cart.getItems() == null ||
                cart.getItems().isEmpty()) {

            throw new BadRequestException(
                    "Cart is empty."
            );
        }

        for (CartItemResponse cartItem : cart.getItems()) {

            ProductResponse product =
                    catalogClient.getProductById(
                            cartItem.getProductId()
                    );

            Integer availableQuantity =
                    inventoryClient.getAvailableQuantity(
                            cartItem.getProductId()
                    );

            if (availableQuantity < cartItem.getQuantity()) {

                throw new BadRequestException(
                        "Insufficient stock for product : "
                                + product.getName()
                );
            }

            OrderItem orderItem =
                    orderItemFactory.createOrderItem(
                            order,
                            cartItem,
                            product
                    );

            order.getOrderItems().add(orderItem);

            inventoryClient.reduceStock(
                    cartItem.getProductId(),
                    cartItem.getQuantity()
            );
        }

        orderFactory.recalculateOrder(order);

        Long paymentId =
                paymentClient.createPayment(
                        order.getOrderId(),
                        order.getTotalAmount()
                );

        order.setPaymentId(paymentId);

        Order savedOrder = orderRepository.save(order);

        notificationClient.sendOrderPlacedNotification(
                savedOrder.getUserId(),
                savedOrder.getOrderId()
        );

        return orderMapper.toResponse(savedOrder);
    }

    @Override
    @Transactional(readOnly = true)
    public PageResponse<OrderResponse> getAllOrders(
            Integer page,
            Integer size,
            String sortBy,
            String sortDir) {

        Page<OrderResponse> result =
                orderRepository.findAll(
                                PageRequestUtil.createPageRequest(
                                        page,
                                        size,
                                        sortBy,
                                        sortDir
                                )
                        )
                        .map(orderMapper::toResponse);

        return PageResponseUtil.from(result);
    }

    @Override
    @Transactional(readOnly = true)
    public OrderResponse getOrderById(Long orderId) {

        Order order = findOrderById(orderId);

        return orderMapper.toResponse(order);
    }

    @Override
    @Transactional(readOnly = true)
    public PageResponse<OrderResponse> getOrdersByUserId(
            Long userId,
            Integer page,
            Integer size,
            String sortBy,
            String sortDir) {

        Page<OrderResponse> result =
                orderRepository.findByUserId(
                                userId,
                                PageRequestUtil.createPageRequest(
                                        page,
                                        size,
                                        sortBy,
                                        sortDir
                                )
                        )
                        .map(orderMapper::toResponse);

        return PageResponseUtil.from(result);
    }

    /**
     * Returns Order by id.
     */
    private Order findOrderById(Long orderId) {

        if (orderId == null) {
            throw new BadRequestException(
                    "Order id cannot be null."
            );
        }

        return orderRepository.findById(orderId)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Order","id",orderId
                        ));
    }

    @Override
    public OrderResponse updateOrderStatus(
            Long orderId,
            UpdateOrderStatusRequest request) {

        if (request == null) {
            throw new BadRequestException(
                    "Update order status request cannot be null."
            );
        }

        Order order = findOrderById(orderId);

        changeOrderState(
                order,
                request.getOrderStatus()
        );

        Order updatedOrder = orderRepository.save(order);

        return orderMapper.toResponse(updatedOrder);
    }

    @Override
    public void deleteOrder(Long orderId) {

        Order order = findOrderById(orderId);

        if (order.getStatus() != OrderStatus.PENDING &&
                order.getStatus() != OrderStatus.CANCELLED) {

            throw new BadRequestException(
                    "Only pending or cancelled orders can be deleted."
            );
        }

        orderRepository.delete(order);
    }

    /**
     * Applies State Pattern.
     */
    private void changeOrderState(
            Order order,
            OrderStatus status) {

        switch (status) {

            case PENDING ->
                    pendingState.handle(order);

            case CONFIRMED ->
                    confirmedState.handle(order);

            case SHIPPED ->
                    shippedState.handle(order);

            case DELIVERED ->
                    deliveredState.handle(order);

            case CANCELLED ->
                    cancelledState.handle(order);

            default ->
                    throw new BadRequestException(
                            "Unsupported order status."
                    );
        }
    }
}