package com.ecommerce.service.factory;

import com.ecommerce.dto.request.ReviewRequest;
import com.ecommerce.exception.ResourceAlreadyExistsException;
import com.ecommerce.exception.ResourceNotFoundException;
import com.ecommerce.mapper.ReviewMapper;
import com.ecommerce.model.Product;
import com.ecommerce.model.Review;
import com.ecommerce.repository.ProductRepository;
import com.ecommerce.repository.ReviewRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ReviewFactory {

    private final ReviewRepository reviewRepository;
    private final ProductRepository productRepository;
    private final ReviewMapper reviewMapper;

    public Review create(ReviewRequest request) {
        return reviewMapper.toEntity(request);
    }

    public Product getProduct(Long productId) {
        return productRepository.findById(productId)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Product", "id", productId));
    }

    public Review getReview(Long reviewId) {
        return reviewRepository.findById(reviewId)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Review", "id", reviewId));
    }

    public void validateDuplicateReview(Product product, Long userId) {
        if (reviewRepository.existsByProductAndUserId(product, userId)) {
            throw new ResourceAlreadyExistsException(
                    "Review",
                    "userId",
                    userId
            );
        }
    }
}