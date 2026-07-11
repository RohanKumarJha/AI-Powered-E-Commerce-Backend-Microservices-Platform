package com.ecommerce.factory;

import com.ecommerce.dto.request.CategoryRequest;
import com.ecommerce.exception.ResourceAlreadyExistsException;
import com.ecommerce.exception.ResourceNotFoundException;
import com.ecommerce.model.Category;
import com.ecommerce.repository.CategoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class CategoryFactory {

    private final CategoryRepository categoryRepository;

    public Category createCategory(CategoryRequest request) {
        if (categoryRepository.existsByNameIgnoreCase(request.getName())) {
            throw new ResourceAlreadyExistsException("Category", "name", request.getName());
        }
        Category parentCategory = null;
        if (request.getParentCategoryId() != null) {
            parentCategory = getCategoryById(request.getParentCategoryId());
        }
        return Category.builder()
                .name(request.getName())
                .description(request.getDescription())
                .parentCategory(parentCategory)
                .imageUrl(request.getImageUrl())
                .active(request.getActive() != null ? request.getActive() : true)
                .build();
    }

    public Category getCategoryById(Long categoryId) {
        return categoryRepository.findById(categoryId)
                .orElseThrow(() -> new ResourceNotFoundException("Category", "categoryId", categoryId));
    }

    public void validateCategoryNameForUpdate(Long categoryId, String name) {
        categoryRepository.findByNameIgnoreCase(name)
                .ifPresent(category -> {
                    if (!category.getCategoryId().equals(categoryId)) {
                        throw new ResourceAlreadyExistsException(
                                "Category",
                                "name",
                                name
                        );
                    }
                });
    }
}