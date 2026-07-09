package com.ecommerce.controller;


import com.ecommerce.dto.request.PaymentRequest;
import com.ecommerce.dto.request.RefundRequest;
import com.ecommerce.dto.response.PaymentResponse;
import com.ecommerce.service.PaymentService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

import jakarta.validation.Valid;

import lombok.RequiredArgsConstructor;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/api/v1/payments")
@RequiredArgsConstructor
@Tag(
        name = "Payment Controller",
        description = "APIs for payment processing and refund management"
)
public class PaymentController {


    private final PaymentService paymentService;



    @PostMapping
    @Operation(
            summary = "Create payment",
            description = "Process a new payment"
    )
    public ResponseEntity<PaymentResponse> createPayment(
            @Valid @RequestBody PaymentRequest request
    ) {

        return new ResponseEntity<>(
                paymentService.createPayment(request),
                HttpStatus.CREATED
        );

    }



    @GetMapping("/{paymentId}")
    @Operation(
            summary = "Get payment by id"
    )
    public ResponseEntity<PaymentResponse> getPaymentById(
            @PathVariable Long paymentId
    ) {

        return ResponseEntity.ok(
                paymentService.getPaymentById(paymentId)
        );

    }



    @GetMapping("/order/{orderId}")
    @Operation(
            summary = "Get payment by order id"
    )
    public ResponseEntity<PaymentResponse> getPaymentByOrderId(
            @PathVariable Long orderId
    ) {

        return ResponseEntity.ok(
                paymentService.getPaymentByOrderId(orderId)
        );

    }



    @GetMapping
    @Operation(
            summary = "Get all payments"
    )
    public ResponseEntity<List<PaymentResponse>> getAllPayments() {


        return ResponseEntity.ok(
                paymentService.getAllPayments()
        );

    }



    @PostMapping("/{paymentId}/refund")
    @Operation(
            summary = "Refund payment"
    )
    public ResponseEntity<PaymentResponse> refundPayment(
            @PathVariable Long paymentId,
            @Valid @RequestBody RefundRequest request
    ) {


        return ResponseEntity.ok(
                paymentService.refundPayment(
                        paymentId,
                        request
                )
        );

    }

}