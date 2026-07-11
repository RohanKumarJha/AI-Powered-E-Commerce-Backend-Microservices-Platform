package com.ecommerce.adapter;


import com.ecommerce.dto.request.ChatRequestDto;
import com.ecommerce.dto.request.ReviewSummaryRequest;
import org.springframework.stereotype.Component;

import java.util.List;


@Component
public class MockAIProviderAdapter implements AIProviderAdapter {


    @Override
    public List<Long> generateRecommendations(
            Long productId
    ) {


        return List.of(
                productId + 10,
                productId + 20,
                productId + 30
        );

    }



    @Override
    public String generateChatResponse(
            ChatRequestDto request
    ) {


        return "AI response for question: "
                + request.getQuestion();

    }



    @Override
    public String generateReviewSummary(
            ReviewSummaryRequest request
    ) {


        return "Generated summary for product: "
                + request.getProductId();

    }

}