package com.ecommerce.service.impl;

import com.ecommerce.dto.request.OrderItemRequest;
import com.ecommerce.dto.response.OrderItemResponse;
import com.ecommerce.service.OrderItemService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OrderItemServiceImpl implements OrderItemService {
    @Override
    public OrderItemResponse addOrderItem(OrderItemRequest request) {
        return null;
    }

    @Override
    public List<OrderItemResponse> getItemsByOrderId(Long orderId) {
        return List.of();
    }

    @Override
    public OrderItemResponse getOrderItemById(Long orderItemId) {
        return null;
    }

    @Override
    public void deleteOrderItem(Long orderItemId) {

    }
}
