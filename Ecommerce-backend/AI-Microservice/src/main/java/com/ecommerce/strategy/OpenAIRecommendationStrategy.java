package com.ecommerce.strategy;

import com.ecommerce.dto.request.RecommendationRequest;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;


@Component
public class OpenAIRecommendationStrategy
        implements AIRecommendationStrategy {


    @Override
    public List<Long> generateRecommendation(
            RecommendationRequest request
    ) {


        /*
          Later this will call AI Adapter.
          Currently returning dummy AI generated products.
        */


        return Arrays.asList(
                request.getProductId() + 1,
                request.getProductId() + 2,
                request.getProductId() + 3
        );

    }

}