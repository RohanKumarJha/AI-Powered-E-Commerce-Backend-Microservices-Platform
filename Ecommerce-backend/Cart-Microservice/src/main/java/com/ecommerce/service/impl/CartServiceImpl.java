package com.ecommerce.service.impl;

import com.ecommerce.dto.request.CartRequest;
import com.ecommerce.dto.request.UpdateCartStatusRequest;
import com.ecommerce.dto.response.CartResponse;
import com.ecommerce.service.CartService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CartServiceImpl implements CartService {
    @Override
    public CartResponse createCart(CartRequest request) {
        return null;
    }

    @Override
    public List<CartResponse> getAllCarts() {
        return List.of();
    }

    @Override
    public CartResponse getCartById(Long cartId) {
        return null;
    }

    @Override
    public CartResponse getCartByUserId(Long userId) {
        return null;
    }

    @Override
    public CartResponse updateCartStatus(Long cartId, UpdateCartStatusRequest request) {
        return null;
    }

    @Override
    public void deleteCart(Long cartId) {

    }
}
