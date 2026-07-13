package com.ecommerce.service.impl;

import com.ecommerce.service.adapter.PaymentGatewayAdapter;
import com.ecommerce.dto.request.PaymentRequest;
import com.ecommerce.dto.request.RefundRequest;
import com.ecommerce.dto.response.PageResponse;
import com.ecommerce.dto.response.PaymentResponse;
import com.ecommerce.dto.response.PaymentStatisticsResponse;
import com.ecommerce.exception.BadRequestException;
import com.ecommerce.exception.ResourceNotFoundException;
import com.ecommerce.service.factory.PaymentFactory;
import com.ecommerce.mapper.PaymentMapper;
import com.ecommerce.model.ENUM.PaymentMethod;
import com.ecommerce.model.ENUM.PaymentStatus;
import com.ecommerce.model.Payment;
import com.ecommerce.repository.PaymentRepository;
import com.ecommerce.security.UserContext;
import com.ecommerce.service.PaymentService;
import com.ecommerce.service.strategy.CodStrategy;
import com.ecommerce.service.strategy.CreditCardStrategy;
import com.ecommerce.service.strategy.PaymentStrategy;
import com.ecommerce.service.strategy.UpiStrategy;
import com.ecommerce.util.PageRequestUtil;
import com.ecommerce.util.PageResponseUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PaymentServiceImpl implements PaymentService {

    private final PaymentRepository paymentRepository;
    private final PaymentMapper paymentMapper;
    private final PaymentFactory paymentFactory;
    private final PaymentGatewayAdapter paymentGatewayAdapter;

    private final CreditCardStrategy creditCardStrategy;
    private final UpiStrategy upiStrategy;
    private final CodStrategy codStrategy;

    @Override
    public PaymentResponse createPayment(PaymentRequest request) {

        if (paymentRepository.existsByOrderId(request.getOrderId())) {
            throw new BadRequestException(
                    "Payment already exists for order id: " + request.getOrderId()
            );
        }

        Payment payment = paymentFactory.createPayment(request);

        PaymentStrategy strategy = getPaymentStrategy(
                payment.getPaymentMethod()
        );

        payment = strategy.processPayment(payment);

        if (payment.getTransactionId() != null) {

            payment.setGatewayReferenceId(
                    paymentGatewayAdapter.processPayment(
                            payment.getAmount()
                    )
            );
        }

        payment = paymentRepository.save(payment);

        return paymentMapper.toResponse(payment);
    }

    @Override
    public PaymentResponse getPaymentById(Long paymentId) {

        Payment payment = paymentRepository
                .findById(paymentId)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Payment not found with id: " + paymentId
                ));

        return paymentMapper.toResponse(payment);
    }

    @Override
    public PaymentResponse getPaymentByOrderId(Long orderId) {

        Payment payment = paymentRepository
                .findByOrderId(orderId)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Payment not found for order id: " + orderId
                ));

        return paymentMapper.toResponse(payment);
    }

    private PaymentStrategy getPaymentStrategy(
            PaymentMethod paymentMethod
    ) {

        return switch (paymentMethod) {

            case CREDIT_CARD -> creditCardStrategy;

            case UPI -> upiStrategy;

            case COD -> codStrategy;

            default -> throw new BadRequestException(
                    "Unsupported payment method: " + paymentMethod
            );
        };

    }

    @Override
    public PageResponse<PaymentResponse> getAllPayments(
            int page,
            int size,
            String sortBy,
            String direction
    ) {

        Pageable pageable = PageRequestUtil.create(
                page,
                size,
                sortBy,
                direction
        );

        Page<PaymentResponse> paymentPage = paymentRepository
                .findAll(pageable)
                .map(paymentMapper::toResponse);

        return PageResponseUtil.create(paymentPage);

    }

    @Override
    public PageResponse<PaymentResponse> getMyPayments(
            int page,
            int size,
            String sortBy,
            String direction
    ) {

        Pageable pageable = PageRequestUtil.create(
                page,
                size,
                sortBy,
                direction
        );

        Page<PaymentResponse> paymentPage = paymentRepository
                .findByUserId(
                        UserContext.getCurrentUserId(),
                        pageable
                )
                .map(paymentMapper::toResponse);

        return PageResponseUtil.create(paymentPage);

    }

    @Override
    public PaymentResponse refundPayment(
            Long paymentId,
            RefundRequest request
    ) {

        Payment payment = paymentRepository
                .findById(paymentId)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Payment not found with id: " + paymentId
                ));

        if (payment.getRefundId() != null) {
            throw new BadRequestException(
                    "Payment has already been refunded."
            );
        }

        if (payment.getTransactionId() == null) {
            throw new BadRequestException(
                    "Refund cannot be processed because payment was not successful."
            );
        }

        if (request.getRefundAmount().compareTo(payment.getAmount()) > 0) {
            throw new BadRequestException(
                    "Refund amount cannot exceed the payment amount."
            );
        }

        String refundReference = paymentGatewayAdapter.refundPayment(
                payment.getTransactionId(),
                request.getRefundAmount()
        );

        payment.setRefundId(refundReference);
        payment.setRefundAmount(request.getRefundAmount());
        payment.setRefundDate(java.time.LocalDateTime.now());
        payment.setPaymentStatus(
                com.ecommerce.model.ENUM.PaymentStatus.REFUNDED
        );
        payment.setUpdatedAt(java.time.LocalDateTime.now());

        payment = paymentRepository.save(payment);

        return paymentMapper.toResponse(payment);

    }

    @Override
    public PaymentStatisticsResponse getPaymentStatistics() {

        return PaymentStatisticsResponse.builder()
                .totalPayments(paymentRepository.count())
                .successfulPayments(
                        paymentRepository.countByPaymentStatus(
                                PaymentStatus.SUCCESS
                        )
                )
                .pendingPayments(
                        paymentRepository.countByPaymentStatus(
                                PaymentStatus.PENDING
                        )
                )
                .failedPayments(
                        paymentRepository.countByPaymentStatus(
                                PaymentStatus.FAILED
                        )
                )
                .refundedPayments(
                        paymentRepository.countByPaymentStatus(
                                PaymentStatus.REFUNDED
                        )
                )
                .totalRevenue(
                        paymentRepository.getTotalRevenue()
                )
                .totalRefundAmount(
                        paymentRepository.getTotalRefundAmount()
                )
                .build();

    }

}