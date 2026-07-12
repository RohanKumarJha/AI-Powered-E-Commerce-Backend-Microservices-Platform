package com.ecommerce.client.cart;

import com.ecommerce.client.cart.response.CartItemResponse;
import com.ecommerce.client.cart.response.CartResponse;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MockCartClient implements CartClient {

    @Override
    public CartResponse getCartByUserId(Long userId) {

        CartItemResponse item1 = CartItemResponse.builder()
                .cartItemId(1L)
                .productId(1L)
                .quantity(2)
                .build();


        CartItemResponse item2 = CartItemResponse.builder()
                .cartItemId(2L)
                .productId(2L)
                .quantity(1)
                .build();


        return CartResponse.builder()
                .cartId(1L)
                .userId(userId)
                .items(List.of(item1, item2))
                .build();
    }
}