package com.ecommerce.service.impl;

import com.ecommerce.client.cart.CartClient;
import com.ecommerce.client.cart.response.CartItemResponse;
import com.ecommerce.client.cart.response.CartResponse;
import com.ecommerce.client.catalog.CatalogClient;
import com.ecommerce.client.catalog.response.ProductResponse;
import com.ecommerce.client.inventory.InventoryClient;
import com.ecommerce.client.notification.NotificationClient;
import com.ecommerce.client.payment.PaymentClient;
import com.ecommerce.dto.request.OrderRequest;
import com.ecommerce.dto.request.UpdateOrderStatusRequest;
import com.ecommerce.dto.response.OrderResponse;
import com.ecommerce.dto.response.PageResponse;
import com.ecommerce.exception.BadRequestException;
import com.ecommerce.exception.ResourceNotFoundException;
import com.ecommerce.mapper.OrderMapper;
import com.ecommerce.model.Order;
import com.ecommerce.model.OrderItem;
import com.ecommerce.model.enums.OrderStatus;
import com.ecommerce.repository.OrderRepository;
import com.ecommerce.security.UserContext;
import com.ecommerce.service.OrderService;
import com.ecommerce.service.factory.OrderFactory;
import com.ecommerce.service.factory.OrderItemFactory;
import com.ecommerce.service.state.CancelledState;
import com.ecommerce.service.state.ConfirmedState;
import com.ecommerce.service.state.DeliveredState;
import com.ecommerce.service.state.PendingState;
import com.ecommerce.service.state.ShippedState;
import com.ecommerce.util.PageRequestUtil;
import com.ecommerce.util.PageResponseUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
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
        log.debug("Fetching cart for userId: {}", userId);
        CartResponse cart = cartClient.getCartByUserId(userId);
        if (cart == null
                || cart.getItems() == null
                || cart.getItems().isEmpty()) {
            throw new BadRequestException(
                    "Cart is empty."
            );
        }
        log.debug(
                "Found {} cart items.",
                cart.getItems().size()
        );
        for (CartItemResponse cartItem : cart.getItems()) {
            log.debug(
                    "Processing productId: {}",
                    cartItem.getProductId()
            );
            ProductResponse product =
                    catalogClient.getProductById(
                            cartItem.getProductId()
                    );
            Integer availableQuantity =
                    inventoryClient.getAvailableQuantity(
                            cartItem.getProductId()
                    );
            log.debug(
                    "Available quantity for productId {} : {}",
                    cartItem.getProductId(),
                    availableQuantity
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
            log.debug(
                    "Reducing stock for productId: {}, quantity: {}",
                    cartItem.getProductId(),
                    cartItem.getQuantity()
            );
            inventoryClient.reduceStock(
                    cartItem.getProductId(),
                    cartItem.getQuantity()
            );
        }
        log.debug("Recalculating order totals.");
        orderFactory.recalculateOrder(order);
        log.debug("Creating payment.");
        Long paymentId =
                paymentClient.createPayment(
                        order.getOrderId(),
                        order.getTotalAmount()
                );
        order.setPaymentId(paymentId);
        log.debug(
                "Payment created successfully. PaymentId: {}",
                paymentId
        );
        Order savedOrder =
                orderRepository.save(order);
        log.info(
                "Order created successfully. OrderId: {}",
                savedOrder.getOrderId()
        );
        notificationClient.sendOrderPlacedNotification(
                savedOrder.getUserId(),
                savedOrder.getOrderId()
        );
        log.debug(
                "Order placed notification sent for OrderId: {}",
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
        log.info(
                "Fetching all orders. page={}, size={}, sortBy={}, sortDir={}",
                page,
                size,
                sortBy,
                sortDir
        );
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
        log.info(
                "Successfully fetched {} orders.",
                result.getNumberOfElements()
        );
        return PageResponseUtil.from(result);
    }

    @Override
    @Transactional(readOnly = true)
    public OrderResponse getOrderById(Long orderId) {
        log.info(
                "Fetching order with id: {}",
                orderId
        );
        Order order = findOrderById(orderId);
        log.info(
                "Order fetched successfully. OrderId: {}",
                orderId
        );
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
        log.info(
                "Fetching orders for userId: {}. page={}, size={}, sortBy={}, sortDir={}",
                userId,
                page,
                size,
                sortBy,
                sortDir
        );
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
        log.info(
                "Successfully fetched {} orders for userId: {}",
                result.getNumberOfElements(),
                userId
        );
        return PageResponseUtil.from(result);
    }

    private Order findOrderById(Long orderId) {
        log.debug(
                "Finding order with id: {}",
                orderId
        );
        if (orderId == null) {
            throw new BadRequestException(
                    "Order id cannot be null."
            );
        }
        return orderRepository.findById(orderId)
                .orElseThrow(() ->
                    new ResourceNotFoundException(
                            "Order",
                            "id",
                            orderId
                    )
                );
    }

    @Override
    public OrderResponse updateOrderStatus(
            Long orderId,
            UpdateOrderStatusRequest request) {
        log.info(
                "Updating order status for orderId: {}",
                orderId
        );
        if (request == null) {
            throw new BadRequestException(
                    "Update order status request cannot be null."
            );
        }
        Order order = findOrderById(orderId);
        log.debug(
                "Changing order status from {} to {}",
                order.getStatus(),
                request.getOrderStatus()
        );
        changeOrderState(
                order,
                request.getOrderStatus()
        );
        Order updatedOrder =
                orderRepository.save(order);
        log.info(
                "Order status updated successfully. OrderId: {}, New Status: {}",
                updatedOrder.getOrderId(),
                updatedOrder.getStatus()
        );
        return orderMapper.toResponse(updatedOrder);
    }

    @Override
    public void deleteOrder(Long orderId) {
        log.info(
                "Deleting order with id: {}",
                orderId
        );
        Order order = findOrderById(orderId);
        if (order.getStatus() != OrderStatus.PENDING
                && order.getStatus() != OrderStatus.CANCELLED) {
            throw new BadRequestException(
                    "Only pending or cancelled orders can be deleted."
            );
        }
        orderRepository.delete(order);
        log.info(
                "Order deleted successfully. OrderId: {}",
                orderId
        );
    }

    private void changeOrderState(
            Order order,
            OrderStatus status) {
        log.debug(
                "Applying state transition for orderId: {} to status: {}",
                order.getOrderId(),
                status
        );
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

            default -> {
                throw new BadRequestException(
                        "Unsupported order status."
                );
            }
        }
        log.debug(
                "Order state changed successfully. OrderId: {}, Current Status: {}",
                order.getOrderId(),
                order.getStatus()
        );
    }
}