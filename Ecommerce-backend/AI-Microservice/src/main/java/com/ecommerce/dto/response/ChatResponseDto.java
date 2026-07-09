package com.ecommerce.dto.response;

import lombok.*;

import java.time.LocalDateTime;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ChatResponseDto {


    private Long chatResponseId;


    private Long chatRequestId;


    private String response;


    private LocalDateTime createdAt;


    private LocalDateTime updatedAt;

}