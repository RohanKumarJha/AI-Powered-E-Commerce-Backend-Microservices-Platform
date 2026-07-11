package com.ecommerce.controller;

import com.ecommerce.dto.request.ProductFilterRequest;
import com.ecommerce.dto.request.ProductImageRequest;
import com.ecommerce.dto.request.ProductRequest;
import com.ecommerce.dto.response.ProductImageResponse;
import com.ecommerce.dto.response.ProductResponse;
import com.ecommerce.dto.response.PageResponse;
import com.ecommerce.service.ProductService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

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
    public PageResponse<ProductResponse> getAllProducts(
            @RequestParam(required = false) Integer page,
            @RequestParam(required = false) Integer size,
            @RequestParam(required = false) String sortBy,
            @RequestParam(required = false) String sortDir) {
        return productService.getAllProducts(page, size, sortBy, sortDir);
    }

    @GetMapping("/search")
    public PageResponse<ProductResponse> searchProducts(
            @RequestParam String keyword,
            @RequestParam(required = false) Integer page,
            @RequestParam(required = false) Integer size,
            @RequestParam(required = false) String sortBy,
            @RequestParam(required = false) String sortDir) {
        return productService.searchProducts(keyword, page, size, sortBy, sortDir);
    }

    @PostMapping("/filter")
    public PageResponse<ProductResponse> filterProducts(
            @RequestBody ProductFilterRequest request,
            @RequestParam(required = false) Integer page,
            @RequestParam(required = false) Integer size,
            @RequestParam(required = false) String sortBy,
            @RequestParam(required = false) String sortDir) {
        return productService.filterProducts(request, page, size, sortBy, sortDir);
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