package com.ecommerce.adapter;

import java.math.BigDecimal;

public interface PaymentGatewayAdapter {


    String processPayment(
            BigDecimal amount
    );


    String refundPayment(
            String transactionId,
            BigDecimal amount
    );

}