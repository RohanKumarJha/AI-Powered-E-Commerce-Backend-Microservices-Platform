package com.ecommerce.service.impl;

import com.ecommerce.client.inventory.InventoryClient;
import com.ecommerce.dto.request.CartItemRequest;
import com.ecommerce.dto.request.UpdateCartItemQuantityRequest;
import com.ecommerce.dto.response.CartItemResponse;
import com.ecommerce.service.factory.CartItemFactory;
import com.ecommerce.mapper.CartItemMapper;
import com.ecommerce.model.Cart;
import com.ecommerce.model.CartItem;
import com.ecommerce.repository.CartItemRepository;
import com.ecommerce.repository.CartRepository;
import com.ecommerce.service.CartItemService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CartItemServiceImpl implements CartItemService {

    private final CartRepository cartRepository;
    private final CartItemRepository cartItemRepository;
    private final CartItemFactory cartItemFactory;
    private final CartItemMapper cartItemMapper;
    private final InventoryClient inventoryClient;

    @Override
    public CartItemResponse addItem(CartItemRequest request) {
        Cart cart = cartItemFactory.getCartById(request.getCartId());
        CartItem cartItem = cartItemRepository
                .findByCartAndProductId(cart, request.getProductId())
                .orElse(null);
        if (cartItem != null) {
            cartItem.setQuantity(
                    cartItem.getQuantity() + request.getQuantity()
            );
            cartItem.setLineTotal(
                    cartItem.getSpecialPriceSnapshot()
                            .multiply(BigDecimal.valueOf(cartItem.getQuantity()))
            );
        } else {
            cartItem = cartItemFactory.createCartItem(cart, request);
        }
        cartItem = cartItemRepository.save(cartItem);

        // Temporary Inventory Communication
        inventoryClient.decreaseStock(
                request.getProductId(),
                request.getQuantity()
        );
        recalculateCart(cart);
        cartRepository.save(cart);
        return cartItemMapper.toResponse(cartItem);
    }

    @Override
    public List<CartItemResponse> getItemsByCart(Long cartId) {
        Cart cart = cartItemFactory.getCartById(cartId);
        return cartItemRepository.findByCart(cart)
                .stream()
                .map(cartItemMapper::toResponse)
                .toList();
    }

    @Override
    public CartItemResponse getItemById(Long cartItemId) {
        return cartItemMapper.toResponse(
                cartItemFactory.getCartItemById(cartItemId)
        );
    }

    @Override
    public CartItemResponse updateQuantity(
            Long cartItemId,
            UpdateCartItemQuantityRequest request) {
        CartItem cartItem = cartItemFactory.getCartItemById(cartItemId);
        int oldQuantity = cartItem.getQuantity();
        int newQuantity = request.getQuantity();
        int difference = newQuantity - oldQuantity;
        cartItem.setQuantity(newQuantity);
        cartItem.setLineTotal(
                cartItem.getSpecialPriceSnapshot()
                        .multiply(BigDecimal.valueOf(newQuantity))
        );
        cartItem = cartItemRepository.save(cartItem);
        if (difference > 0) {
            inventoryClient.decreaseStock(
                    cartItem.getProductId(),
                    difference
            );
        } else if (difference < 0) {
            inventoryClient.increaseStock(
                    cartItem.getProductId(),
                    Math.abs(difference)
            );
        }
        Cart cart = cartItem.getCart();
        recalculateCart(cart);
        cartRepository.save(cart);
        return cartItemMapper.toResponse(cartItem);
    }

    @Override
    public void removeItem(Long cartItemId) {

        CartItem cartItem = cartItemFactory.getCartItemById(cartItemId);

        Cart cart = cartItem.getCart();

        // Restore inventory
        inventoryClient.increaseStock(
                cartItem.getProductId(),
                cartItem.getQuantity()
        );

        cartItemRepository.delete(cartItem);

        recalculateCart(cart);

        cartRepository.save(cart);
    }


    // ************************ Helper Methods *******************************
    private void recalculateCart(Cart cart) {

        List<CartItem> cartItems = cartItemRepository.findByCart(cart);

        int totalItems = cartItems.size();

        int totalQuantity = cartItems.stream()
                .mapToInt(CartItem::getQuantity)
                .sum();

        BigDecimal totalAmount = cartItems.stream()
                .map(item ->
                        item.getPriceSnapshot()
                                .multiply(BigDecimal.valueOf(item.getQuantity()))
                )
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        BigDecimal grandTotal = cartItems.stream()
                .map(CartItem::getLineTotal)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        BigDecimal totalDiscount = totalAmount.subtract(grandTotal);

        cart.setTotalItems(totalItems);
        cart.setTotalQuantity(totalQuantity);
        cart.setTotalAmount(totalAmount);
        cart.setTotalDiscount(totalDiscount);
        cart.setGrandTotal(grandTotal);
    }
}