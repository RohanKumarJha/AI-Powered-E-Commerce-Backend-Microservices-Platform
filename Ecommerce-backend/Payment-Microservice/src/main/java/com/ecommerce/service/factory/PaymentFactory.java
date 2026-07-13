package com.ecommerce.service.factory;

import com.ecommerce.client.order.OrderClient;
import com.ecommerce.client.order.response.OrderResponse;
import com.ecommerce.dto.request.PaymentRequest;
import com.ecommerce.exception.BadRequestException;
import com.ecommerce.mapper.PaymentMapper;
import com.ecommerce.model.ENUM.PaymentStatus;
import com.ecommerce.model.Payment;
import com.ecommerce.security.UserContext;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
@RequiredArgsConstructor
public class PaymentFactory {

    private final PaymentMapper paymentMapper;
    private final OrderClient orderClient;

    public Payment createPayment(PaymentRequest request) {

        OrderResponse order = orderClient.getOrderById(
                request.getOrderId()
        );

        if (order == null) {
            throw new BadRequestException(
                    "Order not found with id: " + request.getOrderId()
            );
        }

        Payment payment = paymentMapper.toEntity(request);

        payment.setUserId(
                UserContext.getCurrentUserId()
        );

        payment.setCurrency("INR");

        payment.setPaymentStatus(
                PaymentStatus.PENDING
        );

        payment.setCreatedAt(
                LocalDateTime.now()
        );

        payment.setUpdatedAt(
                LocalDateTime.now()
        );

        return payment;
    }
}