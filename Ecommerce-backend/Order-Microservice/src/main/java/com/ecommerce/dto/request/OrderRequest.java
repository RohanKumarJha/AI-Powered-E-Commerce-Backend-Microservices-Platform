package com.ecommerce.dto.request;

import jakarta.validation.constraints.NotNull;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class OrderRequest {

    @NotNull(message = "User id is required")
    private Long userId;

    @NotNull(message = "Cart id is required")
    private Long cartId;
}