package com.ecommerce.dto.response;

import lombok.*;

import java.time.LocalDateTime;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ReviewSummaryResponse {


    private Long reviewSummaryId;


    private Long productId;


    private String summary;


    private LocalDateTime generatedAt;


    private LocalDateTime createdAt;


    private LocalDateTime updatedAt;

}