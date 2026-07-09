package com.ecommerce.factory;

import com.ecommerce.dto.request.ProductImageRequest;
import com.ecommerce.mapper.ProductImageMapper;
import com.ecommerce.model.ProductImage;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ProductImageFactory {

    private final ProductImageMapper productImageMapper;

    public ProductImage create(ProductImageRequest request) {
        return productImageMapper.toEntity(request);
    }
}