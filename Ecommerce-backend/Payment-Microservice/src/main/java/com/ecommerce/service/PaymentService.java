package com.ecommerce.service;

import com.ecommerce.dto.request.PaymentRequest;
import com.ecommerce.dto.request.RefundRequest;
import com.ecommerce.dto.response.PaymentResponse;

import java.util.List;

public interface PaymentService {


    PaymentResponse createPayment(
            PaymentRequest request
    );


    PaymentResponse getPaymentById(
            Long paymentId
    );


    PaymentResponse getPaymentByOrderId(
            Long orderId
    );


    List<PaymentResponse> getAllPayments();


    PaymentResponse refundPayment(
            Long paymentId,
            RefundRequest request
    );

}