package com.ecommerce.dto.response;

import lombok.*;

import java.time.LocalDateTime;
import java.util.List;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RecommendationResponse {


    private Long recommendationId;


    private Long userId;


    private Long productId;


    private List<Long> recommendedProductIds;


    private LocalDateTime generatedAt;


    private LocalDateTime createdAt;


    private LocalDateTime updatedAt;

}