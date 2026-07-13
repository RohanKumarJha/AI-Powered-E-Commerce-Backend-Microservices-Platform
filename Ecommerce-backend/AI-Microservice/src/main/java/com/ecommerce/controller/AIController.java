package com.ecommerce.controller;

import com.ecommerce.dto.request.ChatRequest;
import com.ecommerce.dto.request.ReviewSummaryRequest;
import com.ecommerce.dto.response.ChatResponse;
import com.ecommerce.dto.response.RecommendationResponse;
import com.ecommerce.dto.response.ReviewSummaryResponse;
import com.ecommerce.service.AIService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/ai")
@Tag(
        name = "AI Controller",
        description = "APIs for product recommendations, AI chat and review summarization."
)
public class AIController {

    private final AIService aiService;

    public AIController(AIService aiService) {
        this.aiService = aiService;
    }

    @GetMapping("/recommendations/{userId}")
    @Operation(summary = "Get product recommendations")
    public ResponseEntity<RecommendationResponse> getRecommendations(
            @PathVariable Long userId,
            @RequestParam(defaultValue = "RULE_BASED") String strategy
    ) {

        return ResponseEntity.ok(
                aiService.getRecommendations(userId, strategy)
        );
    }

    @PostMapping("/chat")
    @Operation(summary = "Chat with AI assistant")
    public ResponseEntity<ChatResponse> chat(
            @Valid
            @RequestBody
            ChatRequest request
    ) {

        return ResponseEntity.ok(
                aiService.chat(request)
        );
    }

    @PostMapping("/reviews/summarize")
    @Operation(summary = "Summarize customer reviews")
    public ResponseEntity<ReviewSummaryResponse> summarizeReviews(
            @Valid
            @RequestBody
            ReviewSummaryRequest request
    ) {

        return ResponseEntity.ok(
                aiService.summarizeReviews(request)
        );
    }

}