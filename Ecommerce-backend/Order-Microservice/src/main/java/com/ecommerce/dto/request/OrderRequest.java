package com.ecommerce.dto.request;

import com.ecommerce.model.ENUM.PaymentMethod;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class OrderRequest {

    @NotNull(message = "Address ID is required.")
    private Long addressId;

    @NotNull(message = "Payment method is required.")
    private PaymentMethod paymentMethod;
}