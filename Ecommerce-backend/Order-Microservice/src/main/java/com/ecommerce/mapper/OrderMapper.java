package com.ecommerce.mapper;

import com.ecommerce.dto.request.OrderRequest;
import com.ecommerce.dto.response.OrderResponse;
import com.ecommerce.model.Order;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class OrderMapper {

    private final ModelMapper modelMapper;

    public Order toEntity(OrderRequest request) {
        return modelMapper.map(request, Order.class);
    }

    public OrderResponse toResponse(Order order) {
        return modelMapper.map(order, OrderResponse.class);
    }
}