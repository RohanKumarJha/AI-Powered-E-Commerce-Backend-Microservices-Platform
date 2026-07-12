package com.ecommerce.client.catalog;

import lombok.*;

import java.math.BigDecimal;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ProductSnapshot {

    private Long productId;

    private Long sellerId;

    private String name;

    private String sku;

    private String mainImageUrl;

    private BigDecimal price;

    private BigDecimal discount;

    private BigDecimal specialPrice;

    private Boolean active;

    private Integer availableQuantity;
}