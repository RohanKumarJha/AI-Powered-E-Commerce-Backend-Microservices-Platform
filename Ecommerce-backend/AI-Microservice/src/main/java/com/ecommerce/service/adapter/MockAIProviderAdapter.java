package com.ecommerce.service.adapter;

import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class MockAIProviderAdapter
        implements AIProviderAdapter {

    @Override
    public String chat(Long userId, String message) {

        return "Mock AI Response: I understand your query - \"" +
                message +
                "\". This feature will be integrated with a real AI provider later.";
    }

    @Override
    public String summarizeReviews(List<String> reviews) {

        return "Summary: Based on " +
                reviews.size() +
                " review(s), customers are generally satisfied with the product.";
    }

}