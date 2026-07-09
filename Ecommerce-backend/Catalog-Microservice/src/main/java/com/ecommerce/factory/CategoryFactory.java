package com.ecommerce.factory;

import com.ecommerce.dto.request.CategoryRequest;
import com.ecommerce.mapper.CategoryMapper;
import com.ecommerce.model.Category;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class CategoryFactory {

    private final CategoryMapper categoryMapper;

    public Category create(CategoryRequest request) {
        return categoryMapper.toEntity(request);
    }
}