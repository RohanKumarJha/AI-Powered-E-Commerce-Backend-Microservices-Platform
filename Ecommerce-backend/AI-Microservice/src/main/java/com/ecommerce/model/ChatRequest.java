package com.ecommerce.model;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "chat_requests")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ChatRequest {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long chatRequestId;

    // Reference to Identity Service
    @Column(nullable = false)
    private Long userId;

    @Column(nullable = false, length = 2000)
    private String question;

    @OneToOne(
            mappedBy = "chatRequest",
            cascade = CascadeType.ALL,
            orphanRemoval = true
    )
    private ChatResponse chatResponse;

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
