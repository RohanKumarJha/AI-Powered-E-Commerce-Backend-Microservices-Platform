package com.ecommerce.controller;

import com.ecommerce.dto.request.ReviewRequest;
import com.ecommerce.dto.response.ReviewResponse;
import com.ecommerce.service.ReviewService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/reviews")
@RequiredArgsConstructor
public class ReviewController {

    private final ReviewService reviewService;

    @PostMapping("/products/{productId}")
    public ReviewResponse addReview(@PathVariable Long productId,
                                    @Valid @RequestBody ReviewRequest request) {
        return reviewService.addReview(productId, request);
    }

    @PutMapping("/{reviewId}")
    public ReviewResponse updateReview(@PathVariable Long reviewId,
                                       @Valid @RequestBody ReviewRequest request) {
        return reviewService.updateReview(reviewId, request);
    }

    @DeleteMapping("/{reviewId}")
    public void deleteReview(@PathVariable Long reviewId) {
        reviewService.deleteReview(reviewId);
    }

    @GetMapping("/products/{productId}")
    public List<ReviewResponse> getProductReviews(@PathVariable Long productId) {
        return reviewService.getProductReviews(productId);
    }
}