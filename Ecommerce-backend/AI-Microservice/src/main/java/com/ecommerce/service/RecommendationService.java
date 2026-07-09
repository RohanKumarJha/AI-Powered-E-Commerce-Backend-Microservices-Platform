package com.ecommerce.service;

import com.ecommerce.dto.request.RecommendationRequest;
import com.ecommerce.dto.response.RecommendationResponse;

import java.util.List;

public interface RecommendationService {


    RecommendationResponse generateRecommendation(
            RecommendationRequest request
    );


    RecommendationResponse getRecommendationById(
            Long recommendationId
    );


    List<RecommendationResponse> getRecommendationsByUser(
            Long userId
    );

}