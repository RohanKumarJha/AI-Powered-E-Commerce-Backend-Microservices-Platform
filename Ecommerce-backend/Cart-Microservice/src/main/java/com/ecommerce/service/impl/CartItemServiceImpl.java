package com.ecommerce.service.impl;

import com.ecommerce.dto.request.CartItemRequest;
import com.ecommerce.dto.request.UpdateCartItemQuantityRequest;
import com.ecommerce.dto.response.CartItemResponse;
import com.ecommerce.service.CartItemService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CartItemServiceImpl implements CartItemService {
    @Override
    public CartItemResponse addItem(CartItemRequest request) {
        return null;
    }

    @Override
    public List<CartItemResponse> getItemsByCart(Long cartId) {
        return List.of();
    }

    @Override
    public CartItemResponse getItemById(Long cartItemId) {
        return null;
    }

    @Override
    public CartItemResponse updateQuantity(Long cartItemId, UpdateCartItemQuantityRequest request) {
        return null;
    }

    @Override
    public void removeItem(Long cartItemId) {

    }
}
