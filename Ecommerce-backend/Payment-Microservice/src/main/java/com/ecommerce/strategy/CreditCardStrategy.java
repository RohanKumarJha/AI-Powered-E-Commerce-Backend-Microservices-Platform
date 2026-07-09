package com.ecommerce.strategy;

import com.ecommerce.model.Payment;
import com.ecommerce.model.ENUM.PaymentStatus;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.UUID;


@Component
public class CreditCardStrategy implements PaymentStrategy {


    @Override
    public Payment processPayment(Payment payment) {


        payment.setPaymentStatus(
                PaymentStatus.SUCCESS
        );


        payment.setTransactionId(
                UUID.randomUUID().toString()
        );


        payment.setGatewayReferenceId(
                "CARD-" + UUID.randomUUID()
        );


        payment.setPaymentDate(
                LocalDateTime.now()
        );


        return payment;
    }
}