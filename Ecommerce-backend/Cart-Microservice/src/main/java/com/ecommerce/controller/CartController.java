package com.ecommerce.controller;

import com.ecommerce.dto.request.UpdateCartStatusRequest;
import com.ecommerce.dto.response.CartResponse;
import com.ecommerce.dto.response.PageResponse;
import com.ecommerce.service.CartService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/carts")
@RequiredArgsConstructor
public class CartController {

    private final CartService cartService;

    @PostMapping
    public ResponseEntity<CartResponse> createCart() {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(cartService.createCart());
    }

    @GetMapping
    public ResponseEntity<PageResponse<CartResponse>> getAllCarts(
            @RequestParam(required = false) Integer page,
            @RequestParam(required = false) Integer size,
            @RequestParam(required = false) String sortBy,
            @RequestParam(required = false) String direction) {
        return ResponseEntity.ok(
                cartService.getAllCarts(
                        page,
                        size,
                        sortBy,
                        direction
                )
        );
    }

    @GetMapping("/{cartId}")
    public ResponseEntity<CartResponse> getCartById(
            @PathVariable Long cartId) {
        return ResponseEntity.ok(
                cartService.getCartById(cartId)
        );
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<CartResponse> getCartByUserId(
            @PathVariable Long userId) {
        return ResponseEntity.ok(
                cartService.getCartByUserId(userId)
        );
    }

    @PutMapping("/{cartId}/status")
    public ResponseEntity<CartResponse> updateCartStatus(
            @PathVariable Long cartId,
            @Valid @RequestBody UpdateCartStatusRequest request) {
        return ResponseEntity.ok(
                cartService.updateCartStatus(cartId, request)
        );
    }

    @DeleteMapping("/{cartId}")
    public ResponseEntity<Void> deleteCart(
            @PathVariable Long cartId) {
        cartService.deleteCart(cartId);
        return ResponseEntity.noContent().build();
    }
}