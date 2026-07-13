package com.ecommerce.service.impl;

import com.ecommerce.dto.request.ChatRequest;
import com.ecommerce.dto.request.ReviewSummaryRequest;
import com.ecommerce.dto.response.ChatResponse;
import com.ecommerce.dto.response.RecommendationResponse;
import com.ecommerce.dto.response.ReviewSummaryResponse;
import com.ecommerce.mapper.RecommendationMapper;
import com.ecommerce.model.Recommendation;
import com.ecommerce.repository.RecommendationRepository;
import com.ecommerce.service.AIService;
import com.ecommerce.service.adapter.AIProviderAdapter;
import com.ecommerce.service.factory.RecommendationFactory;
import com.ecommerce.service.factory.RecommendationStrategyFactory;
import com.ecommerce.service.strategy.RecommendationStrategy;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
public class AIServiceImpl implements AIService {

    private final RecommendationRepository recommendationRepository;
    private final RecommendationMapper recommendationMapper;
    private final RecommendationFactory recommendationFactory;
    private final RecommendationStrategyFactory strategyFactory;
    private final AIProviderAdapter aiProviderAdapter;

    public AIServiceImpl(
            RecommendationRepository recommendationRepository,
            RecommendationMapper recommendationMapper,
            RecommendationFactory recommendationFactory,
            RecommendationStrategyFactory strategyFactory,
            AIProviderAdapter aiProviderAdapter
    ) {
        this.recommendationRepository = recommendationRepository;
        this.recommendationMapper = recommendationMapper;
        this.recommendationFactory = recommendationFactory;
        this.strategyFactory = strategyFactory;
        this.aiProviderAdapter = aiProviderAdapter;
    }

    @Override
    public RecommendationResponse getRecommendations(
            Long userId,
            String strategy
    ) {

        RecommendationStrategy recommendationStrategy =
                strategyFactory.getStrategy(strategy);

        Set<Long> productIds =
                recommendationStrategy.recommendProducts(userId);

        Recommendation recommendation =
                recommendationRepository
                        .findByUserIdAndActiveTrue(userId)
                        .map(existing -> recommendationFactory
                                .updateRecommendation(
                                        existing,
                                        productIds,
                                        userId
                                ))
                        .orElseGet(() ->
                                recommendationFactory.createRecommendation(
                                        userId,
                                        productIds,
                                        userId
                                ));

        recommendation =
                recommendationRepository.save(recommendation);

        return recommendationMapper.toResponse(recommendation);
    }

    @Override
    public ChatResponse chat(ChatRequest request) {

        String answer = aiProviderAdapter.chat(
                request.getUserId(),
                request.getMessage()
        );

        return ChatResponse.builder()
                .userId(request.getUserId())
                .question(request.getMessage())
                .answer(answer)
                .build();
    }

    @Override
    public ReviewSummaryResponse summarizeReviews(
            ReviewSummaryRequest request
    ) {

        String summary = aiProviderAdapter
                .summarizeReviews(request.getReviews());

        return ReviewSummaryResponse.builder()
                .summary(summary)
                .build();
    }

}