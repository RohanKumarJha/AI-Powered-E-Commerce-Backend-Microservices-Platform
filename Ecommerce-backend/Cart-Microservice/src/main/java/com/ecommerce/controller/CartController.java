package com.ecommerce.controller;

import com.ecommerce.dto.request.CartRequest;
import com.ecommerce.dto.request.UpdateCartStatusRequest;
import com.ecommerce.dto.response.CartResponse;
import com.ecommerce.service.CartService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/carts")
@RequiredArgsConstructor
public class CartController {

    private final CartService cartService;


    @PostMapping
    public ResponseEntity<CartResponse> createCart(
            @Valid @RequestBody CartRequest request) {
        return new ResponseEntity<>(
                cartService.createCart(request),
                HttpStatus.CREATED
        );
    }


    @GetMapping
    public ResponseEntity<List<CartResponse>> getAllCarts() {
        return ResponseEntity.ok(cartService.getAllCarts());
    }


    @GetMapping("/{cartId}")
    public ResponseEntity<CartResponse> getCartById(@PathVariable Long cartId) {
        return ResponseEntity.ok(cartService.getCartById(cartId));
    }


    @GetMapping("/user/{userId}")
    public ResponseEntity<CartResponse> getCartByUserId(@PathVariable Long userId) {
        return ResponseEntity.ok(cartService.getCartByUserId(userId));
    }


    @PutMapping("/{cartId}/status")
    public ResponseEntity<CartResponse> updateCartStatus(
            @PathVariable Long cartId,
            @Valid @RequestBody UpdateCartStatusRequest request) {
        return ResponseEntity.ok(
                cartService.updateCartStatus(cartId, request));
    }


    @DeleteMapping("/{cartId}")
    public ResponseEntity<Void> deleteCart(
            @PathVariable Long cartId) {
        cartService.deleteCart(cartId);
        return ResponseEntity.noContent().build();
    }
}