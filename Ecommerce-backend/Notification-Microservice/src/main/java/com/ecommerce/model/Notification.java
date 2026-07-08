package com.ecommerce.model;

import com.ecommerce.model.ENUM.NotificationStatus;
import com.ecommerce.model.ENUM.NotificationType;
import com.ecommerce.model.ENUM.ReferenceType;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "notifications")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Notification {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long notificationId;


    // Reference to Identity Service
    @Column(nullable = false)
    private Long userId;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private NotificationType notificationType;

    @Column(nullable = false, length = 200)
    private String title;

    @Column(nullable = false, length = 2000)
    private String message;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    @Builder.Default
    private NotificationStatus notificationStatus = NotificationStatus.PENDING;

    // OrderId, PaymentId, UserId, etc.
    private Long referenceId;

    @Enumerated(EnumType.STRING)
    private ReferenceType referenceType;

    private LocalDateTime sentAt;

    @Column(nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @Column(nullable = false)
    private LocalDateTime updatedAt;

    @PrePersist
    public void prePersist() {
        createdAt = LocalDateTime.now();
        updatedAt = createdAt;
    }

    @PreUpdate
    public void preUpdate() {
        updatedAt = LocalDateTime.now();
    }
}

