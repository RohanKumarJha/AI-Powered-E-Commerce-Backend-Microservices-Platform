package com.ecommerce.model;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "recommendations")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Recommendation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long recommendationId;

    // Optional - who requested recommendation
    private Long userId;

    // Reference to Catalog Service
    @Column(nullable = false)
    private Long productId;

    @ElementCollection
    @CollectionTable(
            name = "recommended_products",
            joinColumns = @JoinColumn(name = "recommendation_id")
    )
    @Column(name = "recommended_product_id")
    @Builder.Default
    private List<Long> recommendedProductIds = new ArrayList<>();

    private LocalDateTime generatedAt;

    @Column(nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @Column(nullable = false)
    private LocalDateTime updatedAt;

    @PrePersist
    public void prePersist() {
        createdAt = LocalDateTime.now();
        updatedAt = createdAt;
        generatedAt = createdAt;
    }

    @PreUpdate
    public void preUpdate() {
        updatedAt = LocalDateTime.now();
    }
}
