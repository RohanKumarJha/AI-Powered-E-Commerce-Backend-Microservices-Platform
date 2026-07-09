package com.ecommerce.dto.response;

import com.ecommerce.model.ENUM.CartStatus;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CartResponse {

    private Long cartId;

    private Long userId;

    private CartStatus status;

    private List<CartItemResponse> items;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;
}