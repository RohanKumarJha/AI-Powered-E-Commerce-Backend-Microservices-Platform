package com.ecommerce.model;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "chat_responses")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ChatResponse {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long chatResponseId;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "chat_request_id", nullable = false)
    private ChatRequest chatRequest;

    @Column(nullable = false, length = 5000)
    private String response;

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
