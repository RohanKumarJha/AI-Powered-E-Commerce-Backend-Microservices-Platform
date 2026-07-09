package com.ecommerce.controller;


import com.ecommerce.dto.request.ReviewSummaryRequest;
import com.ecommerce.dto.response.ReviewSummaryResponse;
import com.ecommerce.service.ReviewSummaryService;

import jakarta.validation.Valid;

import lombok.RequiredArgsConstructor;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import org.springframework.web.bind.annotation.*;



@RestController
@RequestMapping("/api/v1/review-summaries")
@RequiredArgsConstructor
public class ReviewSummaryController {


    private final ReviewSummaryService reviewSummaryService;




    @PostMapping
    public ResponseEntity<ReviewSummaryResponse> generateSummary(
            @Valid @RequestBody ReviewSummaryRequest request
    ){

        return new ResponseEntity<>(
                reviewSummaryService.generateSummary(request),
                HttpStatus.CREATED
        );

    }





    @GetMapping("/product/{productId}")
    public ResponseEntity<ReviewSummaryResponse> getSummaryByProduct(
            @PathVariable Long productId
    ){

        return ResponseEntity.ok(
                reviewSummaryService
                        .getSummaryByProductId(productId)
        );

    }

}