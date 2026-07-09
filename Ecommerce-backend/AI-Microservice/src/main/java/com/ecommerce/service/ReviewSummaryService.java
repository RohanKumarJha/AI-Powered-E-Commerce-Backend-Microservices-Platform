package com.ecommerce.service;

import com.ecommerce.dto.request.ReviewSummaryRequest;
import com.ecommerce.dto.response.ReviewSummaryResponse;

public interface ReviewSummaryService {


    ReviewSummaryResponse generateSummary(
            ReviewSummaryRequest request
    );


    ReviewSummaryResponse getSummaryByProductId(
            Long productId
    );

}