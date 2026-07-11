package com.ecommerce.service.impl;

import com.ecommerce.dto.request.ReviewRequest;
import com.ecommerce.dto.response.ReviewResponse;
import com.ecommerce.factory.ReviewFactory;
import com.ecommerce.mapper.ReviewMapper;
import com.ecommerce.model.Product;
import com.ecommerce.model.Review;
import com.ecommerce.repository.ReviewRepository;
import com.ecommerce.service.ReviewService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ReviewServiceImpl implements ReviewService {

    private final ReviewRepository reviewRepository;
    private final ReviewFactory reviewFactory;
    private final ReviewMapper reviewMapper;

    @Override
    public ReviewResponse addReview(Long productId, ReviewRequest request) {

        Product product = reviewFactory.getProduct(productId);

        reviewFactory.validateDuplicateReview(
                product,
                request.getUserId()
        );

        Review review = reviewFactory.create(request);

        review.setProduct(product);

        return reviewMapper.toResponse(
                reviewRepository.save(review)
        );
    }

    @Override
    public ReviewResponse updateReview(Long reviewId,
                                       ReviewRequest request) {

        Review review = reviewFactory.getReview(reviewId);

        reviewMapper.updateFromRequest(request, review);

        return reviewMapper.toResponse(
                reviewRepository.save(review)
        );
    }

    @Override
    public void deleteReview(Long reviewId) {

        Review review = reviewFactory.getReview(reviewId);

        reviewRepository.delete(review);
    }

    @Override
    public List<ReviewResponse> getProductReviews(Long productId) {

        Product product = reviewFactory.getProduct(productId);

        return reviewRepository.findByProduct(product)
                .stream()
                .map(reviewMapper::toResponse)
                .toList();
    }

}