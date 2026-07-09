package com.ecommerce.adapter;

import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.UUID;


@Component
public class MockPaymentGatewayAdapter implements PaymentGatewayAdapter {


    @Override
    public String processPayment(
            BigDecimal amount
    ) {

        return "TXN-" + UUID.randomUUID();

    }



    @Override
    public String refundPayment(
            String transactionId,
            BigDecimal amount
    ) {

        return "REFUND-" + UUID.randomUUID();

    }

}