package com.ecommerce.service.factory;

import com.ecommerce.client.cart.response.CartItemResponse;
import com.ecommerce.client.catalog.response.ProductImageResponse;
import com.ecommerce.client.catalog.response.ProductResponse;
import com.ecommerce.exception.BadRequestException;
import com.ecommerce.model.Order;
import com.ecommerce.model.OrderItem;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Comparator;
import java.util.Objects;

@Slf4j
@Component
public class OrderItemFactory {

    private static final BigDecimal DEFAULT_DISCOUNT = BigDecimal.ZERO;

    public OrderItem createOrderItem(
            Order order,
            CartItemResponse cartItem,
            ProductResponse product) {
        log.debug(
                "Creating order item for productId: {}",
                product != null ? product.getProductId() : null
        );
        validateOrder(order);
        validateCartItem(cartItem);
        validateProduct(product);
        OrderItem orderItem = OrderItem.builder()
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
        log.debug(
                "Order item created successfully for productId: {}",
                product.getProductId()
        );
        return orderItem;
    }

    public BigDecimal calculateLineTotal(
            BigDecimal specialPrice,
            Integer quantity) {
        log.debug(
                "Calculating line total. SpecialPrice: {}, Quantity: {}",
                specialPrice,
                quantity
        );
        Objects.requireNonNull(
                specialPrice,
                "Special price cannot be null."
        );
        Objects.requireNonNull(
                quantity,
                "Quantity cannot be null."
        );
        BigDecimal lineTotal = specialPrice
                .multiply(BigDecimal.valueOf(quantity))
                .setScale(2, RoundingMode.HALF_UP);
        log.debug("Calculated line total: {}", lineTotal);
        return lineTotal;
    }

    private void validateCartItem(CartItemResponse cartItem) {
        log.debug("Validating cart item.");
        if (cartItem == null) {
            log.warn("Cart item cannot be null.");
            throw new BadRequestException(
                    "Cart item cannot be null."
            );
        }
        if (cartItem.getProductId() == null) {
            log.warn("Product id is required.");
            throw new BadRequestException(
                    "Product id is required."
            );
        }
        validateQuantity(cartItem.getQuantity());
    }

    private void validateProduct(ProductResponse product) {
        log.debug(
                "Validating product: {}",
                product != null ? product.getProductId() : null
        );
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

    private void validateOrder(Order order) {
        log.debug("Validating order.");
        if (order == null) {
            throw new BadRequestException(
                    "Order cannot be null."
            );
        }
    }

    public void validateQuantity(Integer quantity) {
        log.debug("Validating quantity: {}", quantity);
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

    private String getPrimaryImage(ProductResponse product) {
        log.debug(
                "Resolving primary image for productId: {}",
                product.getProductId()
        );
        if (product.getImages() == null
                || product.getImages().isEmpty()) {
            log.debug(
                    "No images found for productId: {}",
                    product.getProductId()
            );
            return null;
        }
        String imageUrl = product.getImages()
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
        log.debug(
                "Primary image resolved successfully for productId: {}",
                product.getProductId()
        );
        return imageUrl;
    }
}