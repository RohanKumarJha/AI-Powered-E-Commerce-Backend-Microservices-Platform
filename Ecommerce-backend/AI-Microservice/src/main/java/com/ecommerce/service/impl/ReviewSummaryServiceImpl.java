package com.ecommerce.service.impl;

import com.ecommerce.dto.request.ReviewSummaryRequest;
import com.ecommerce.dto.response.ReviewSummaryResponse;
import com.ecommerce.service.ReviewSummaryService;
import org.springframework.stereotype.Service;

@Service
public class ReviewSummaryServiceImpl implements ReviewSummaryService {
    @Override
    public ReviewSummaryResponse generateSummary(ReviewSummaryRequest request) {
        return null;
    }

    @Override
    public ReviewSummaryResponse getSummaryByProductId(Long productId) {
        return null;
    }
}
