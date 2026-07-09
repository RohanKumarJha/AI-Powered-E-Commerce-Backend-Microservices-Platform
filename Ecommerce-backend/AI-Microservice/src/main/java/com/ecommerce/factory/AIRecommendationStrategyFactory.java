package com.ecommerce.strategy;


import org.springframework.stereotype.Component;

@Component
public class AIRecommendationStrategyFactory {


    private final OpenAIRecommendationStrategy openAIStrategy;

    private final MockRecommendationStrategy mockStrategy;



    public AIRecommendationStrategyFactory(
            OpenAIRecommendationStrategy openAIStrategy,
            MockRecommendationStrategy mockStrategy
    ) {

        this.openAIStrategy = openAIStrategy;
        this.mockStrategy = mockStrategy;
    }



    public AIRecommendationStrategy getStrategy(
            String type
    ) {


        if(type == null){

            return mockStrategy;

        }


        return switch(type.toUpperCase()){


            case "OPENAI" ->
                    openAIStrategy;


            case "MOCK" ->
                    mockStrategy;


            default ->
                    throw new IllegalArgumentException(
                            "Invalid AI strategy type"
                    );

        };

    }

}