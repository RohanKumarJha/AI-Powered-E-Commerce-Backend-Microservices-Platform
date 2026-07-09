package com.ecommerce.factory;

import com.ecommerce.dto.request.ReviewRequest;
import com.ecommerce.mapper.ReviewMapper;
import com.ecommerce.model.Review;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ReviewFactory {

    private final ReviewMapper reviewMapper;

    public Review create(ReviewRequest request) {
        return reviewMapper.toEntity(request);
    }
}