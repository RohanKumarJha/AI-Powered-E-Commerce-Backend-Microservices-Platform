package com.ecommerce.dto.request;

import com.ecommerce.model.ENUM.CartStatus;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UpdateCartStatusRequest {

    @NotNull(message = "Cart status is required")
    private CartStatus status;
}