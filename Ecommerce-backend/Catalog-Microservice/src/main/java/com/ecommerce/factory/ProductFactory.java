package com.ecommerce.factory;

import com.ecommerce.dto.request.ProductRequest;
import com.ecommerce.exception.ResourceAlreadyExistsException;
import com.ecommerce.exception.ResourceNotFoundException;
import com.ecommerce.mapper.ProductMapper;
import com.ecommerce.model.Brand;
import com.ecommerce.model.Category;
import com.ecommerce.model.Product;
import com.ecommerce.repository.BrandRepository;
import com.ecommerce.repository.CategoryRepository;
import com.ecommerce.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ProductFactory {

    private final ProductRepository productRepository;
    private final CategoryRepository categoryRepository;
    private final BrandRepository brandRepository;
    private final ProductMapper productMapper;

    public Product create(ProductRequest request) {
        validateDuplicateSku(request.getSku());
        Product product = productMapper.toEntity(request);
        Category category = getCategoryById(request.getCategoryId());
        Brand brand = getBrandById(request.getBrandId());
        product.setCategory(category);
        product.setBrand(brand);
        return product;
    }

    public Product getProductById(Long productId) {
        return productRepository.findById(productId)
                .orElseThrow(() -> new ResourceNotFoundException("Product", "id", productId));
    }

    public Category getCategoryById(Long categoryId) {
        return categoryRepository.findById(categoryId)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Category", "id", categoryId));
    }

    public Brand getBrandById(Long brandId) {
        return brandRepository.findById(brandId)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Brand", "id", brandId));
    }

    public void validateDuplicateSku(String sku) {
        if (productRepository.existsBySkuIgnoreCase(sku)) {
            throw new ResourceAlreadyExistsException("Product", "sku", sku);
        }
    }

    public void validateSkuForUpdate(Long productId, String sku) {
        productRepository.findBySkuIgnoreCase(sku)
                .ifPresent(existingProduct -> {
                    if (!existingProduct.getProductId().equals(productId)) {
                        throw new ResourceAlreadyExistsException("Product", "sku", sku);
                    }
                });
    }
}