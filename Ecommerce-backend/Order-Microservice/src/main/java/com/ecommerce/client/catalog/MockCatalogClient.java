package com.ecommerce.client.catalog;

import com.ecommerce.client.catalog.response.BrandResponse;
import com.ecommerce.client.catalog.response.CategoryResponse;
import com.ecommerce.client.catalog.response.ProductImageResponse;
import com.ecommerce.client.catalog.response.ProductResponse;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Set;

@Service
public class MockCatalogClient implements CatalogClient {

    @Override
    public ProductResponse getProductById(Long productId) {

        ProductImageResponse image = ProductImageResponse.builder()
                .productImageId(1L)
                .imageUrl("https://dummyimage.com/product.jpg")
                .displayOrder(1)
                .primaryImage(true)
                .build();

        return ProductResponse.builder()
                .productId(productId)
                .name("Mock Product")
                .description("Mock Product Description")
                .sku("SKU-" + productId)
                .price(new BigDecimal("1000"))
                .discount(new BigDecimal("100"))
                .specialPrice(new BigDecimal("900"))
                .sellerId(1L)
                .category(CategoryResponse.builder().build())
                .brand(BrandResponse.builder().build())
                .averageRating(4.5)
                .reviewCount(25)
                .active(true)
                .images(Set.of(image))
                .createdAt(LocalDateTime.now())
                .updatedAt(LocalDateTime.now())
                .build();
    }
}