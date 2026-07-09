package com.ecommerce.dto.response;

import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class NotificationPreferenceResponse {


    private Long notificationPreferenceId;


    private Long userId;


    private Boolean emailEnabled;


    private Boolean smsEnabled;


    private Boolean pushEnabled;


    private Boolean orderNotification;


    private Boolean paymentNotification;


    private Boolean promotionNotification;


    private LocalDateTime createdAt;


    private LocalDateTime updatedAt;

}