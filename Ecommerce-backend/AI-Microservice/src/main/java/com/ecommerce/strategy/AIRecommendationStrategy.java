package com.ecommerce.strategy;

import com.ecommerce.dto.request.RecommendationRequest;

import java.util.List;

public interface AIRecommendationStrategy {


    List<Long> generateRecommendation(
            RecommendationRequest request
    );

}