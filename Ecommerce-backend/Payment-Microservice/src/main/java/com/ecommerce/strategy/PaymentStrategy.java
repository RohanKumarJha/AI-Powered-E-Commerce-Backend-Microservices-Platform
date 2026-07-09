package com.ecommerce.strategy;

import com.ecommerce.model.Payment;

public interface PaymentStrategy {

    Payment processPayment(Payment payment);

}