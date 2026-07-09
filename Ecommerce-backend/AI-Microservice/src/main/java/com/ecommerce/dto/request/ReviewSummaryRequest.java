package com.ecommerce.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ReviewSummaryRequest {


    @NotNull(message = "Product id is required")
    private Long productId;


    @NotBlank(message = "Review content cannot be empty")
    private String reviews;

}