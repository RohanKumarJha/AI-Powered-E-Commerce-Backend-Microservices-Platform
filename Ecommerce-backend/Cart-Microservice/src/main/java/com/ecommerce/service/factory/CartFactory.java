package com.ecommerce.service.factory;

import com.ecommerce.exception.ResourceAlreadyExistsException;
import com.ecommerce.exception.ResourceNotFoundException;
import com.ecommerce.model.Cart;
import com.ecommerce.repository.CartRepository;
import com.ecommerce.security.UserContext;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class CartFactory {

    private final CartRepository cartRepository;

    public Cart createCart() {

        Long userId = UserContext.getCurrentUserId();

        validateDuplicateCart(userId);

        return Cart.builder()
                .userId(userId)
                .build();
    }

    public Cart getCartById(Long cartId) {
        return cartRepository.findById(cartId)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Cart",
                                "id",
                                cartId
                        ));
    }

    public Cart getCartByUserId(Long userId) {

        return cartRepository.findByUserId(userId)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Cart",
                                "userId",
                                userId
                        ));
    }

    public void validateDuplicateCart(Long userId) {

        if (cartRepository.existsByUserId(userId)) {
            throw new ResourceAlreadyExistsException(
                    "Cart",
                    "userId",
                    userId
            );
        }
    }
}