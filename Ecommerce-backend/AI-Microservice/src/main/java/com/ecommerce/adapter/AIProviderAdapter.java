package com.ecommerce.adapter;

import com.ecommerce.dto.request.ChatRequestDto;
import com.ecommerce.dto.request.ReviewSummaryRequest;

import java.util.List;

public interface AIProviderAdapter {


    List<Long> generateRecommendations(Long productId);


    String generateChatResponse(
            ChatRequestDto request
    );


    String generateReviewSummary(
            ReviewSummaryRequest request
    );

}