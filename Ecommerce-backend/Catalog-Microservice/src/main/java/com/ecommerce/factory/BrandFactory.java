package com.ecommerce.factory;

import com.ecommerce.dto.request.BrandRequest;
import com.ecommerce.mapper.BrandMapper;
import com.ecommerce.model.Brand;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class BrandFactory {

    private final BrandMapper brandMapper;

    public Brand create(BrandRequest request) {
        return brandMapper.toEntity(request);
    }
}