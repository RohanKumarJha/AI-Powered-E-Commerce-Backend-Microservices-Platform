package com.ecommerce.service.client;

import com.ecommerce.dto.client.ProductResponse;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
public class MockCatalogClient implements CatalogClient {

    @Override
    public ProductResponse getProductById(Long productId) {

        return ProductResponse.builder()
                .productId(productId)
                .name("Dummy Product")
                .sku("SKU-" + productId)
                .price(BigDecimal.valueOf(1000))
                .discount(BigDecimal.valueOf(100))
                .specialPrice(BigDecimal.valueOf(900))
                .build();
    }
}
