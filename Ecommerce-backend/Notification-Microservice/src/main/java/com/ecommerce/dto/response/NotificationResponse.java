package com.ecommerce.dto.response;

import com.ecommerce.model.ENUM.NotificationStatus;
import com.ecommerce.model.ENUM.NotificationType;
import com.ecommerce.model.ENUM.ReferenceType;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class NotificationResponse {

    private Long notificationId;

    private Long userId;

    private NotificationType notificationType;

    private String title;

    private String message;

    private NotificationStatus notificationStatus;

    private Long referenceId;

    private ReferenceType referenceType;

    private LocalDateTime sentAt;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;
}