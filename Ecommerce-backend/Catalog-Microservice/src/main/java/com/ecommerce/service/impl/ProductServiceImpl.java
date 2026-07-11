package com.ecommerce.service.impl;

import com.ecommerce.dto.request.ProductFilterRequest;
import com.ecommerce.dto.request.ProductImageRequest;
import com.ecommerce.dto.request.ProductRequest;
import com.ecommerce.dto.response.ProductImageResponse;
import com.ecommerce.dto.response.ProductResponse;
import com.ecommerce.factory.ProductFactory;
import com.ecommerce.factory.ProductImageFactory;
import com.ecommerce.mapper.ProductImageMapper;
import com.ecommerce.mapper.ProductMapper;
import com.ecommerce.model.Product;
import com.ecommerce.model.ProductImage;
import com.ecommerce.repository.ProductImageRepository;
import com.ecommerce.repository.ProductRepository;
import com.ecommerce.service.ProductService;
import com.ecommerce.dto.response.PageResponse;
import com.ecommerce.specification.ProductSpecification;
import com.ecommerce.util.UserContext;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import com.ecommerce.util.PageRequestUtil;
import com.ecommerce.util.PageResponseUtil;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;
    private final ProductImageRepository productImageRepository;
    private final ProductFactory productFactory;
    private final ProductImageFactory productImageFactory;
    private final ProductMapper productMapper;
    private final ProductImageMapper productImageMapper;

    @Override
    public ProductResponse createProduct(ProductRequest request) {
        Product product = productFactory.create(request);
        Long userId = UserContext.getCurrentUserId();
        product.setSellerId(userId);
        product.setCreatedBy(userId);
        product.setUpdatedBy(userId);
        product.setSpecialPrice(request.getPrice().subtract(request.getDiscount()));
        return productMapper.toResponse(productRepository.save(product));
    }

    @Override
    public ProductResponse updateProduct(Long productId, ProductRequest request) {
        Product product = productFactory.getProductById(productId);
        productFactory.validateSkuForUpdate(productId, request.getSku());
        productMapper.updateFromRequest(request, product);
        product.setCategory(productFactory.getCategoryById(request.getCategoryId()));
        product.setBrand(productFactory.getBrandById(request.getBrandId()));
        product.setSpecialPrice(request.getPrice().subtract(request.getDiscount()));
        product.setUpdatedBy(UserContext.getCurrentUserId());
        return productMapper.toResponse(productRepository.save(product));
    }

    @Override
    public void deleteProduct(Long productId) {
        Product product = productFactory.getProductById(productId);
        productRepository.delete(product);
    }

    @Override
    public ProductResponse getProductById(Long productId) {
        Product product = productFactory.getProductById(productId);
        return productMapper.toResponse(product);
    }

    @Override
    public PageResponse<ProductResponse> getAllProducts(
            Integer page,
            Integer size,
            String sortBy,
            String sortDir) {
        Page<ProductResponse> result =
                productRepository
                        .findAll(PageRequestUtil.createPageRequest(
                                page,
                                size,
                                sortBy,
                                sortDir))
                        .map(productMapper::toResponse);
        return PageResponseUtil.from(result);
    }

    @Override
    public PageResponse<ProductResponse> searchProducts(
            String keyword,
            Integer page,
            Integer size,
            String sortBy,
            String sortDir) {
        Page<ProductResponse> result =
                productRepository.findByNameContainingIgnoreCase(
                                keyword,
                                PageRequestUtil.createPageRequest(
                                        page,
                                        size,
                                        sortBy,
                                        sortDir))
                        .map(productMapper::toResponse);
        return PageResponseUtil.from(result);
    }

    @Override
    public PageResponse<ProductResponse> filterProducts(
            ProductFilterRequest request,
            Integer page,
            Integer size,
            String sortBy,
            String sortDir) {
        Page<ProductResponse> result =
                productRepository
                        .findAll(
                                ProductSpecification.filter(request),
                                PageRequestUtil.createPageRequest(
                                        page,
                                        size,
                                        sortBy,
                                        sortDir))
                        .map(productMapper::toResponse);
        return PageResponseUtil.from(result);
    }

    @Override
    public List<ProductResponse> sortProducts(String sortBy) {
        Sort sort = Sort.by(sortBy).ascending();
        return productRepository.findAll(sort)
                .stream()
                .map(productMapper::toResponse)
                .toList();
    }

    @Override
    public Page<ProductResponse> getProducts(int page, int size) {
        PageRequest pageRequest = PageRequest.of(page, size);
        return productRepository.findAll(pageRequest)
                .map(productMapper::toResponse);
    }

    @Override
    public ProductImageResponse uploadProductImage(Long productId,
                                                   ProductImageRequest request) {
        ProductImage productImage =
                productImageFactory.create(productId, request);
        Long userId = UserContext.getCurrentUserId();
        productImage.setCreatedBy(userId);
        productImage.setUpdatedBy(userId);
        return productImageMapper.toResponse(
                productImageRepository.save(productImage));
    }

    @Override
    public void deleteProductImage(Long imageId) {
        ProductImage productImage =
                productImageFactory.getProductImageById(imageId);
        productImageRepository.delete(productImage);
    }
}