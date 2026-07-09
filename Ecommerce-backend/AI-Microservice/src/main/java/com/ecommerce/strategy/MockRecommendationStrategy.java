package com.ecommerce.strategy;

import com.ecommerce.dto.request.RecommendationRequest;
import org.springframework.stereotype.Component;

import java.util.List;


@Component
public class MockRecommendationStrategy implements AIRecommendationStrategy {


    @Override
    public List<Long> generateRecommendation(
            RecommendationRequest request
    ) {


        return List.of(
                101L,
                102L,
                103L
        );

    }

}