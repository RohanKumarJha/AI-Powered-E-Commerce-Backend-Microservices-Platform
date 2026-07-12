package com.ecommerce.client.payment;

import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
public class MockPaymentClient implements PaymentClient {

    @Override
    public Long createPayment(Long orderId, BigDecimal amount) {
        return 1001L;
    }
}