package com.ecommerce.mapper;

import com.ecommerce.dto.request.CartRequest;
import com.ecommerce.dto.response.CartResponse;
import com.ecommerce.model.Cart;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class CartMapper {

    private final ModelMapper modelMapper;

    public Cart toEntity(CartRequest request) {
        return modelMapper.map(request, Cart.class);
    }

    public CartResponse toResponse(Cart cart) {
        return modelMapper.map(cart, CartResponse.class);
    }
}