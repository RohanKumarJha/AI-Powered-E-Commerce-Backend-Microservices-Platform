package com.ecommerce.service;

import com.ecommerce.dto.request.CartRequest;
import com.ecommerce.dto.request.UpdateCartStatusRequest;
import com.ecommerce.dto.response.CartResponse;

import java.util.List;

public interface CartService {

    CartResponse createCart(CartRequest request);

    List<CartResponse> getAllCarts();

    CartResponse getCartById(Long cartId);

    CartResponse getCartByUserId(Long userId);

    CartResponse updateCartStatus(
            Long cartId,
            UpdateCartStatusRequest request
    );

    void deleteCart(Long cartId);
}