package com.ecommerce.dto.request;

import jakarta.validation.constraints.NotNull;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class NotificationPreferenceRequest {


    @NotNull(message = "User id is required")
    private Long userId;


    private Boolean emailEnabled;


    private Boolean smsEnabled;


    private Boolean pushEnabled;


    private Boolean orderNotification;


    private Boolean paymentNotification;


    private Boolean promotionNotification;

}