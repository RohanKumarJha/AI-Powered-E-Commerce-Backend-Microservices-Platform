package com.ecommerce.service.impl;

import com.ecommerce.dto.request.RecommendationRequest;
import com.ecommerce.dto.response.RecommendationResponse;
import com.ecommerce.service.RecommendationService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RecommendationServiceImpl implements RecommendationService {
    @Override
    public RecommendationResponse generateRecommendation(RecommendationRequest request) {
        return null;
    }

    @Override
    public RecommendationResponse getRecommendationById(Long recommendationId) {
        return null;
    }

    @Override
    public List<RecommendationResponse> getRecommendationsByUser(Long userId) {
        return List.of();
    }
}
