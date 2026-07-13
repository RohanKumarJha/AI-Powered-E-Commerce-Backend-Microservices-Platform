package com.ecommerce.service.strategy;

import com.ecommerce.client.catalog.CatalogClient;
import com.ecommerce.client.catalog.response.ProductResponse;
import org.springframework.stereotype.Component;

import java.util.Set;
import java.util.stream.Collectors;

@Component("RULE_BASED")
public class RuleBasedRecommendationStrategy implements RecommendationStrategy {

    private final CatalogClient catalogClient;

    public RuleBasedRecommendationStrategy(
            CatalogClient catalogClient
    ) {
        this.catalogClient = catalogClient;
    }

    @Override
    public Set<Long> recommendProducts(Long userId) {

        return catalogClient.getProducts()
                .stream()
                .filter(ProductResponse::getActive)
                .limit(5)
                .map(ProductResponse::getProductId)
                .collect(Collectors.toSet());
    }

}