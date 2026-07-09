package com.ecommerce.service.impl;

import com.ecommerce.dto.request.ProductFilterRequest;
import com.ecommerce.dto.request.ProductImageRequest;
import com.ecommerce.dto.request.ProductRequest;
import com.ecommerce.dto.response.ProductImageResponse;
import com.ecommerce.dto.response.ProductResponse;
import com.ecommerce.service.ProductService;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductServiceImpl implements ProductService {
    @Override
    public ProductResponse createProduct(ProductRequest request) {
        return null;
    }

    @Override
    public ProductResponse updateProduct(Long productId, ProductRequest request) {
        return null;
    }

    @Override
    public void deleteProduct(Long productId) {

    }

    @Override
    public ProductResponse getProductById(Long productId) {
        return null;
    }

    @Override
    public List<ProductResponse> getAllProducts() {
        return List.of();
    }

    @Override
    public List<ProductResponse> searchProducts(String keyword) {
        return List.of();
    }

    @Override
    public List<ProductResponse> filterProducts(ProductFilterRequest request) {
        return List.of();
    }

    @Override
    public List<ProductResponse> sortProducts(String sortBy) {
        return List.of();
    }

    @Override
    public Page<ProductResponse> getProducts(int page, int size) {
        return null;
    }

    @Override
    public ProductImageResponse uploadProductImage(Long productId, ProductImageRequest request) {
        return null;
    }

    @Override
    public void deleteProductImage(Long imageId) {

    }
}
