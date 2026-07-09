package com.ecommerce.service;

import com.ecommerce.dto.request.ProductFilterRequest;
import com.ecommerce.dto.request.ProductImageRequest;
import com.ecommerce.dto.request.ProductRequest;
import com.ecommerce.dto.response.ProductImageResponse;
import com.ecommerce.dto.response.ProductResponse;
import org.springframework.data.domain.Page;

import java.util.List;

public interface ProductService {

    ProductResponse createProduct(ProductRequest request);

    ProductResponse updateProduct(Long productId, ProductRequest request);

    void deleteProduct(Long productId);

    ProductResponse getProductById(Long productId);

    List<ProductResponse> getAllProducts();

    List<ProductResponse> searchProducts(String keyword);

    List<ProductResponse> filterProducts(ProductFilterRequest request);

    List<ProductResponse> sortProducts(String sortBy);

    Page<ProductResponse> getProducts(int page, int size);

    ProductImageResponse uploadProductImage(Long productId, ProductImageRequest request);

    void deleteProductImage(Long imageId);
}