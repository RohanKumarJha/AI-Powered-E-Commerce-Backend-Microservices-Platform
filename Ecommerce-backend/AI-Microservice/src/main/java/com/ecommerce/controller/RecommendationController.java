package com.ecommerce.controller;


import com.ecommerce.dto.request.RecommendationRequest;
import com.ecommerce.dto.response.RecommendationResponse;
import com.ecommerce.service.RecommendationService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


import java.util.List;


@RestController
@RequestMapping("/api/v1/recommendations")
@RequiredArgsConstructor
public class RecommendationController {


    private final RecommendationService recommendationService;



    @PostMapping
    public ResponseEntity<RecommendationResponse> generateRecommendation(
            @Valid @RequestBody RecommendationRequest request
    ){

        return new ResponseEntity<>(
                recommendationService.generateRecommendation(request),
                HttpStatus.CREATED
        );

    }




    @GetMapping("/{id}")
    public ResponseEntity<RecommendationResponse> getRecommendationById(
            @PathVariable Long id
    ){

        return ResponseEntity.ok(
                recommendationService.getRecommendationById(id)
        );

    }




    @GetMapping("/user/{userId}")
    public ResponseEntity<List<RecommendationResponse>> getRecommendationsByUser(
            @PathVariable Long userId
    ){

        return ResponseEntity.ok(
                recommendationService.getRecommendationsByUser(userId)
        );

    }

}