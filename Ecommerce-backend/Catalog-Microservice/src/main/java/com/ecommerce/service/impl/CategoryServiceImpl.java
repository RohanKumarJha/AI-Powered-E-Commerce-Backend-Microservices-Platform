package com.ecommerce.service.impl;

import com.ecommerce.dto.request.CategoryRequest;
import com.ecommerce.dto.response.CategoryResponse;
import com.ecommerce.exception.BadRequestException;
import com.ecommerce.factory.CategoryFactory;
import com.ecommerce.mapper.CategoryMapper;
import com.ecommerce.model.Category;
import com.ecommerce.repository.CategoryRepository;
import com.ecommerce.service.CategoryService;
import com.ecommerce.security.UserContext;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CategoryServiceImpl implements CategoryService {

    private final CategoryRepository categoryRepository;
    private final CategoryFactory categoryFactory;
    private final CategoryMapper categoryMapper;

    @Override
    public CategoryResponse createCategory(CategoryRequest request) {
        Category category = categoryFactory.createCategory(request);
        Long userId = UserContext.getCurrentUserId();
        category.setCreatedBy(userId);
        category.setUpdatedBy(userId);
        return categoryMapper.toResponse(categoryRepository.save(category));
    }

    @Override
    public CategoryResponse updateCategory(Long categoryId, CategoryRequest request) {
        Category category = categoryFactory.getCategoryById(categoryId);
        categoryFactory.validateCategoryNameForUpdate(
                categoryId,
                request.getName()
        );
        categoryMapper.updateFromRequest(request, category);
        if (request.getParentCategoryId() != null) {
            Category parentCategory =
                    categoryFactory.getCategoryById(request.getParentCategoryId());
            if (parentCategory.getCategoryId().equals(category.getCategoryId())) {
                throw new BadRequestException(
                        "Category cannot be its own parent."
                );
            }
            category.setParentCategory(parentCategory);
        } else {
            category.setParentCategory(null);
        }
        category.setUpdatedBy(UserContext.getCurrentUserId());
        return categoryMapper.toResponse(categoryRepository.save(category));
    }

    @Override
    public void deleteCategory(Long categoryId) {
        Category category = categoryFactory.getCategoryById(categoryId);
        boolean hasChildCategories =
                categoryRepository.existsByParentCategoryCategoryId(categoryId);
        if (hasChildCategories) {
            throw new BadRequestException(
                    "Cannot delete category because child categories are referring to this parent category."
            );
        }
        categoryRepository.delete(category);
    }

    @Override
    public CategoryResponse getCategoryById(Long categoryId) {
        Category category = categoryFactory.getCategoryById(categoryId);
        return categoryMapper.toResponse(category);
    }

    @Override
    public List<CategoryResponse> getAllCategories() {
        return categoryRepository.findAll()
                .stream()
                .map(categoryMapper::toResponse)
                .toList();
    }
}