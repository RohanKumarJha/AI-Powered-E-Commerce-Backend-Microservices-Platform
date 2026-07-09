package com.ecommerce.service.impl;

import com.ecommerce.dto.request.PaymentRequest;
import com.ecommerce.dto.request.RefundRequest;
import com.ecommerce.dto.response.PaymentResponse;
import com.ecommerce.service.PaymentService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PaymentServiceImpl implements PaymentService {
    @Override
    public PaymentResponse createPayment(PaymentRequest request) {
        return null;
    }

    @Override
    public PaymentResponse getPaymentById(Long paymentId) {
        return null;
    }

    @Override
    public PaymentResponse getPaymentByOrderId(Long orderId) {
        return null;
    }

    @Override
    public List<PaymentResponse> getAllPayments() {
        return List.of();
    }

    @Override
    public PaymentResponse refundPayment(Long paymentId, RefundRequest request) {
        return null;
    }
}
