package com.ecommerce.model;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "notification_preferences")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class NotificationPreference {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long notificationPreferenceId;

    // Reference to Identity Service
    @Column(nullable = false, unique = true)
    private Long userId;

    @Builder.Default
    @Column(nullable = false)
    private Boolean emailEnabled = true;

    @Builder.Default
    @Column(nullable = false)
    private Boolean smsEnabled = true;

    @Builder.Default
    @Column(nullable = false)
    private Boolean pushEnabled = true;

    @Builder.Default
    @Column(nullable = false)
    private Boolean orderNotification = true;

    @Builder.Default
    @Column(nullable = false)
    private Boolean paymentNotification = true;

    @Builder.Default
    @Column(nullable = false)
    private Boolean promotionNotification = true;

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
