package com.ecommerce.dto.request;

import jakarta.validation.constraints.NotNull;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RecommendationRequest {


    @NotNull(message = "User id is required")
    private Long userId;


    @NotNull(message = "Product id is required")
    private Long productId;

}