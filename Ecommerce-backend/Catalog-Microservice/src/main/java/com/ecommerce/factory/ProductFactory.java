package com.ecommerce.factory;

import com.ecommerce.dto.request.ProductRequest;
import com.ecommerce.mapper.ProductMapper;
import com.ecommerce.model.Product;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ProductFactory {

    private final ProductMapper productMapper;

    public Product create(ProductRequest request) {
        return productMapper.toEntity(request);
    }
}