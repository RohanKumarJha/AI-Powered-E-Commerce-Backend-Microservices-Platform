package com.ecommerce.service.factory;

import com.ecommerce.exception.BadRequestException;
import com.ecommerce.service.strategy.RecommendationStrategy;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
public class RecommendationStrategyFactory {

    private final Map<String, RecommendationStrategy> strategies;

    public RecommendationStrategyFactory(
            Map<String, RecommendationStrategy> strategies
    ) {
        this.strategies = strategies;
    }

    public RecommendationStrategy getStrategy(String strategy) {

        RecommendationStrategy recommendationStrategy =
                strategies.get(strategy.toUpperCase());

        if (recommendationStrategy == null) {
            throw new BadRequestException(
                    "Unsupported recommendation strategy: " + strategy
            );
        }

        return recommendationStrategy;
    }

}