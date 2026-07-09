package com.ecommerce.controller;

import com.ecommerce.dto.request.CartItemRequest;
import com.ecommerce.dto.request.UpdateCartItemQuantityRequest;
import com.ecommerce.dto.response.CartItemResponse;
import com.ecommerce.service.CartItemService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/cart-items")
@RequiredArgsConstructor
public class CartItemController {

    private final CartItemService cartItemService;


    @PostMapping
    public ResponseEntity<CartItemResponse> addItem(
            @Valid @RequestBody CartItemRequest request) {
        return new ResponseEntity<>(
                cartItemService.addItem(request),
                HttpStatus.CREATED
        );
    }


    @GetMapping("/cart/{cartId}")
    public ResponseEntity<List<CartItemResponse>> getItemsByCart(
            @PathVariable Long cartId) {
        return ResponseEntity.ok(
                cartItemService.getItemsByCart(cartId)
        );
    }


    @GetMapping("/{cartItemId}")
    public ResponseEntity<CartItemResponse> getItemById(
            @PathVariable Long cartItemId) {
        return ResponseEntity.ok(
                cartItemService.getItemById(cartItemId)
        );
    }


    @PutMapping("/{cartItemId}/quantity")
    public ResponseEntity<CartItemResponse> updateQuantity(
            @PathVariable Long cartItemId,
            @Valid @RequestBody UpdateCartItemQuantityRequest request) {
        return ResponseEntity.ok(
                cartItemService.updateQuantity(cartItemId, request)
        );
    }


    @DeleteMapping("/{cartItemId}")
    public ResponseEntity<Void> removeItem(
            @PathVariable Long cartItemId) {
        cartItemService.removeItem(cartItemId);
        return ResponseEntity.noContent().build();
    }
}