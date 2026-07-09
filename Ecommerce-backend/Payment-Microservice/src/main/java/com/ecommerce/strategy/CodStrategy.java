package com.ecommerce.strategy;

import com.ecommerce.model.Payment;
import com.ecommerce.model.ENUM.PaymentStatus;
import org.springframework.stereotype.Component;


@Component
public class CodStrategy implements PaymentStrategy {


    @Override
    public Payment processPayment(Payment payment) {


        payment.setPaymentStatus(
                PaymentStatus.PENDING
        );


        return payment;
    }
}