package com.ecommerce.controller;

import com.ecommerce.dto.request.ProductFilterRequest;
import com.ecommerce.dto.request.ProductImageRequest;
import com.ecommerce.dto.request.ProductRequest;
import com.ecommerce.dto.response.ProductImageResponse;
import com.ecommerce.dto.response.ProductResponse;
import com.ecommerce.service.ProductService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/products")
@RequiredArgsConstructor
public class ProductController {

    private final ProductService productService;

    @PostMapping
    public ProductResponse createProduct(@Valid @RequestBody ProductRequest request) {
        return productService.createProduct(request);
    }

    @PutMapping("/{productId}")
    public ProductResponse updateProduct(@PathVariable Long productId,
                                         @Valid @RequestBody ProductRequest request) {
        return productService.updateProduct(productId, request);
    }

    @DeleteMapping("/{productId}")
    public void deleteProduct(@PathVariable Long productId) {
        productService.deleteProduct(productId);
    }

    @GetMapping("/{productId}")
    public ProductResponse getProductById(@PathVariable Long productId) {
        return productService.getProductById(productId);
    }

    @GetMapping
    public List<ProductResponse> getAllProducts() {
        return productService.getAllProducts();
    }

    @GetMapping("/search")
    public List<ProductResponse> searchProducts(@RequestParam String keyword) {
        return productService.searchProducts(keyword);
    }

    @PostMapping("/filter")
    public List<ProductResponse> filterProducts(@RequestBody ProductFilterRequest request) {
        return productService.filterProducts(request);
    }

    @GetMapping("/sort")
    public List<ProductResponse> sortProducts(@RequestParam String sortBy) {
        return productService.sortProducts(sortBy);
    }

    @GetMapping("/page")
    public Page<ProductResponse> getProducts(@RequestParam int page,
                                             @RequestParam int size) {
        return productService.getProducts(page, size);
    }

    @PostMapping("/{productId}/images")
    public ProductImageResponse uploadProductImage(@PathVariable Long productId,
                                                   @Valid @RequestBody ProductImageRequest request) {
        return productService.uploadProductImage(productId, request);
    }

    @DeleteMapping("/images/{imageId}")
    public void deleteProductImage(@PathVariable Long imageId) {
        productService.deleteProductImage(imageId);
    }
}