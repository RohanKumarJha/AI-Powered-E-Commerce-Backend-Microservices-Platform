package com.ecommerce.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ChatRequestDto {


    @NotNull(message = "User id is required")
    private Long userId;


    @NotBlank(message = "Question cannot be empty")
    private String question;

}