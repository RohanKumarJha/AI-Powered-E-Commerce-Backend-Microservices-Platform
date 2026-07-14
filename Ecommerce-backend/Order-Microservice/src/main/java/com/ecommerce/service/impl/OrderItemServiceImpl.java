package com.ecommerce.service.impl;

import com.ecommerce.dto.request.OrderItemRequest;
import com.ecommerce.dto.response.OrderItemResponse;
import com.ecommerce.exception.BadRequestException;
import com.ecommerce.exception.ResourceNotFoundException;
import com.ecommerce.mapper.OrderItemMapper;
import com.ecommerce.model.OrderItem;
import com.ecommerce.repository.OrderItemRepository;
import com.ecommerce.service.OrderItemService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional
public class OrderItemServiceImpl implements OrderItemService {

    private final OrderItemRepository orderItemRepository;
    private final OrderItemMapper orderItemMapper;

    @Override
    public OrderItemResponse addOrderItem(OrderItemRequest request) {
        log.warn(
                "Manual creation of order items is not allowed."
        );
        throw new BadRequestException(
                "Order items are created automatically during checkout."
        );
    }

    @Override
    @Transactional(readOnly = true)
    public List<OrderItemResponse> getItemsByOrderId(Long orderId) {
        log.info(
                "Fetching order items for orderId: {}",
                orderId
        );
        if (orderId == null) {
            throw new BadRequestException(
                    "Order id cannot be null."
            );
        }
        List<OrderItemResponse> responses = orderItemRepository
                .findByOrderOrderId(orderId)
                .stream()
                .map(orderItemMapper::toResponse)
                .toList();
        log.info(
                "Fetched {} order items for orderId: {}",
                responses.size(),
                orderId
        );
        return responses;
    }

    @Override
    @Transactional(readOnly = true)
    public OrderItemResponse getOrderItemById(Long orderItemId) {
        log.info(
                "Fetching order item with id: {}",
                orderItemId
        );
        return orderItemMapper.toResponse(
                findOrderItemById(orderItemId)
        );
    }

    @Override
    public void deleteOrderItem(Long orderItemId) {
        log.info(
                "Deleting order item with id: {}",
                orderItemId
        );
        OrderItem orderItem = findOrderItemById(orderItemId);
        orderItemRepository.delete(orderItem);
        log.info(
                "Order item deleted successfully. Id: {}",
                orderItemId
        );
    }

    private OrderItem findOrderItemById(Long orderItemId) {
        log.debug(
                "Finding order item with id: {}",
                orderItemId
        );
        if (orderItemId == null) {
            throw new BadRequestException(
                    "Order item id cannot be null."
            );
        }
        return orderItemRepository.findById(orderItemId)
                .orElseThrow(() ->
                    new ResourceNotFoundException(
                            "OrderItem",
                            "id",
                            orderItemId
                    )
                );
    }
}