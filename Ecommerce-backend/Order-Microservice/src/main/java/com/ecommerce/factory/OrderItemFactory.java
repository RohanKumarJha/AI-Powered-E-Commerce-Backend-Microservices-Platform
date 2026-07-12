package com.ecommerce.factory;

import com.ecommerce.client.cart.response.CartItemResponse;
import com.ecommerce.client.catalog.response.ProductImageResponse;
import com.ecommerce.client.catalog.response.ProductResponse;
import com.ecommerce.exception.BadRequestException;
import com.ecommerce.model.Order;
import com.ecommerce.model.OrderItem;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Comparator;
import java.util.Objects;

@Component
public class OrderItemFactory {

    private static final BigDecimal DEFAULT_DISCOUNT = BigDecimal.ZERO;

    /**
     * Creates OrderItem entity.
     */
    public OrderItem createOrderItem(
            Order order,
            CartItemResponse cartItem,
            ProductResponse product) {

        validateOrder(order);
        validateCartItem(cartItem);
        validateProduct(product);

        return OrderItem.builder()
                .order(order)
                .productId(product.getProductId())
                .sellerId(product.getSellerId())
                .productNameSnapshot(product.getName())
                .skuSnapshot(product.getSku())
                .mainImageUrlSnapshot(getPrimaryImage(product))
                .priceSnapshot(product.getPrice())
                .discountSnapshot(
                        product.getDiscount() == null
                                ? DEFAULT_DISCOUNT
                                : product.getDiscount()
                )
                .specialPriceSnapshot(product.getSpecialPrice())
                .quantity(cartItem.getQuantity())
                .lineTotal(
                        calculateLineTotal(
                                product.getSpecialPrice(),
                                cartItem.getQuantity()
                        )
                )
                .build();
    }

    /**
     * Calculates line total.
     */
    public BigDecimal calculateLineTotal(
            BigDecimal specialPrice,
            Integer quantity) {

        Objects.requireNonNull(
                specialPrice,
                "Special price cannot be null."
        );

        Objects.requireNonNull(
                quantity,
                "Quantity cannot be null."
        );

        return specialPrice
                .multiply(BigDecimal.valueOf(quantity))
                .setScale(2, RoundingMode.HALF_UP);
    }

    /**
     * Validates OrderItem request.
     */
    private void validateCartItem(CartItemResponse cartItem) {

        if (cartItem == null) {
            throw new BadRequestException(
                    "Cart item cannot be null."
            );
        }

        if (cartItem.getProductId() == null) {
            throw new BadRequestException(
                    "Product id is required."
            );
        }

        validateQuantity(cartItem.getQuantity());
    }

    /**
     * Validates product.
     */
    private void validateProduct(ProductResponse product) {

        if (product == null) {
            throw new BadRequestException(
                    "Product not found."
            );
        }

        if (Boolean.FALSE.equals(product.getActive())) {
            throw new BadRequestException(
                    "Product is inactive."
            );
        }

        if (product.getPrice() == null) {
            throw new BadRequestException(
                    "Product price is missing."
            );
        }

        if (product.getSpecialPrice() == null) {
            throw new BadRequestException(
                    "Product special price is missing."
            );
        }
    }

    /**
     * Validates order.
     */
    private void validateOrder(Order order) {

        if (order == null) {
            throw new BadRequestException(
                    "Order cannot be null."
            );
        }
    }

    /**
     * Validates quantity.
     */
    public void validateQuantity(Integer quantity) {

        if (quantity == null) {
            throw new BadRequestException(
                    "Quantity is required."
            );
        }

        if (quantity <= 0) {
            throw new BadRequestException(
                    "Quantity must be greater than zero."
            );
        }
    }

    /**
     * Returns primary image URL.
     */
    private String getPrimaryImage(ProductResponse product) {

        if (product.getImages() == null
                || product.getImages().isEmpty()) {
            return null;
        }

        return product.getImages()
                .stream()
                .filter(ProductImageResponse::getPrimaryImage)
                .findFirst()
                .orElse(
                        product.getImages()
                                .stream()
                                .min(
                                        Comparator.comparing(
                                                ProductImageResponse::getDisplayOrder
                                        )
                                )
                                .orElse(null)
                )
                .getImageUrl();
    }
}