package com.ecommerce.service.factory;

import com.ecommerce.dto.request.OrderRequest;
import com.ecommerce.exception.BadRequestException;
import com.ecommerce.model.ENUM.OrderStatus;
import com.ecommerce.model.ENUM.PaymentMethod;
import com.ecommerce.model.Order;
import com.ecommerce.security.UserContext;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.util.UUID;

@Component
public class OrderFactory {

    private static final BigDecimal DEFAULT_AMOUNT = BigDecimal.ZERO;
    private static final int ESTIMATED_DELIVERY_DAYS = 5;
    private static final BigDecimal TAX_RATE = new BigDecimal("0.18");
    private static final BigDecimal SHIPPING_CHARGE = new BigDecimal("50");
    private static final BigDecimal FREE_SHIPPING_LIMIT = new BigDecimal("500");

    /**
     * Creates new Order.
     */
    public Order createOrder(OrderRequest request) {

        validateOrderRequest(request);

        return Order.builder()
                .orderNumber(generateOrderNumber())
                .userId(UserContext.getCurrentUserId())
                .addressId(request.getAddressId())
                .paymentMethod(request.getPaymentMethod())
                .status(OrderStatus.PENDING)
                .subtotal(DEFAULT_AMOUNT)
                .discount(DEFAULT_AMOUNT)
                .shippingCharge(DEFAULT_AMOUNT)
                .tax(DEFAULT_AMOUNT)
                .totalAmount(DEFAULT_AMOUNT)
                .estimatedDeliveryDate(calculateEstimatedDeliveryDate())
                .build();
    }

    /**
     * Recalculates all order totals.
     */
    public void recalculateOrder(Order order) {

        BigDecimal subtotal = calculateSubtotal(order);
        BigDecimal discount = calculateDiscount(order);
        BigDecimal shipping = calculateShippingCharge(subtotal);
        BigDecimal tax = calculateTax(subtotal.subtract(discount));
        BigDecimal total = calculateTotalAmount(
                subtotal,
                discount,
                shipping,
                tax
        );

        order.setSubtotal(subtotal);
        order.setDiscount(discount);
        order.setShippingCharge(shipping);
        order.setTax(tax);
        order.setTotalAmount(total);
    }

    /**
     * Updates delivery address.
     */
    public void updateAddress(
            Order order,
            Long addressId) {

        if (order == null) {
            throw new BadRequestException(
                    "Order cannot be null."
            );
        }

        if (addressId == null) {
            throw new BadRequestException(
                    "Address id cannot be null."
            );
        }

        order.setAddressId(addressId);
    }

    /**
     * Updates payment method.
     */
    public void updatePaymentMethod(
            Order order,
            PaymentMethod paymentMethod) {

        if (order == null) {
            throw new BadRequestException(
                    "Order cannot be null."
            );
        }

        if (paymentMethod == null) {
            throw new BadRequestException(
                    "Payment method cannot be null."
            );
        }

        order.setPaymentMethod(paymentMethod);
    }

    /**
     * Checks whether order contains items.
     */
    public boolean hasItems(Order order) {

        return order != null
                && order.getOrderItems() != null
                && !order.getOrderItems().isEmpty();
    }

    /**
     * Checks whether total amount is valid.
     */
    public boolean hasValidTotal(Order order) {

        return order != null
                && order.getTotalAmount() != null
                && order.getTotalAmount()
                .compareTo(BigDecimal.ZERO) > 0;
    }

    /**
     * Validates order before checkout.
     */
    public void validateBeforeCheckout(Order order) {

        if (order == null) {
            throw new BadRequestException(
                    "Order cannot be null."
            );
        }

        if (!hasItems(order)) {
            throw new BadRequestException(
                    "Order must contain at least one item."
            );
        }

        if (!hasValidTotal(order)) {
            throw new BadRequestException(
                    "Order total amount must be greater than zero."
            );
        }

        if (order.getAddressId() == null) {
            throw new BadRequestException(
                    "Delivery address is required."
            );
        }

        if (order.getPaymentMethod() == null) {
            throw new BadRequestException(
                    "Payment method is required."
            );
        }
    }

    /**
     * Checks whether order can be edited.
     */
    public boolean isEditable(Order order) {

        if (order == null) {
            return false;
        }

        return order.getStatus() == OrderStatus.PENDING;
    }

    /**
     * Validates editable order.
     */
    public void validateEditableOrder(Order order) {

        if (!isEditable(order)) {
            throw new BadRequestException(
                    "Order cannot be modified after confirmation."
            );
        }
    }

    /**
     * Calculates subtotal.
     */
    private BigDecimal calculateSubtotal(Order order) {

        if (order.getOrderItems() == null
                || order.getOrderItems().isEmpty()) {
            return BigDecimal.ZERO;
        }

        return order.getOrderItems()
                .stream()
                .map(item -> item.getLineTotal() == null
                        ? BigDecimal.ZERO
                        : item.getLineTotal())
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    /**
     * Calculates total discount.
     */
    private BigDecimal calculateDiscount(Order order) {

        if (order.getOrderItems() == null
                || order.getOrderItems().isEmpty()) {
            return BigDecimal.ZERO;
        }

        return order.getOrderItems()
                .stream()
                .map(item -> {

                    BigDecimal original =
                            item.getPriceSnapshot()
                                    .multiply(
                                            BigDecimal.valueOf(
                                                    item.getQuantity()));

                    BigDecimal discounted =
                            item.getSpecialPriceSnapshot()
                                    .multiply(
                                            BigDecimal.valueOf(
                                                    item.getQuantity()));

                    return original.subtract(discounted);
                })
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    /**
     * Calculates shipping charge.
     */
    private BigDecimal calculateShippingCharge(BigDecimal subtotal) {

        if (subtotal == null) {
            return SHIPPING_CHARGE;
        }

        if (subtotal.compareTo(FREE_SHIPPING_LIMIT) >= 0) {
            return BigDecimal.ZERO;
        }

        return SHIPPING_CHARGE;
    }

    /**
     * Calculates tax.
     */
    private BigDecimal calculateTax(BigDecimal taxableAmount) {

        if (taxableAmount == null
                || taxableAmount.compareTo(BigDecimal.ZERO) <= 0) {
            return BigDecimal.ZERO;
        }

        return taxableAmount
                .multiply(TAX_RATE)
                .setScale(2, RoundingMode.HALF_UP);
    }

    /**
     * Calculates final payable amount.
     */
    private BigDecimal calculateTotalAmount(
            BigDecimal subtotal,
            BigDecimal discount,
            BigDecimal shipping,
            BigDecimal tax) {

        return subtotal
                .subtract(discount)
                .add(shipping)
                .add(tax)
                .setScale(2, RoundingMode.HALF_UP);
    }

    /**
     * Generates unique order number.
     */
    private String generateOrderNumber() {

        return "ORD-"
                + UUID.randomUUID()
                .toString()
                .replace("-", "")
                .substring(0, 10)
                .toUpperCase();
    }

    /**
     * Calculates estimated delivery date.
     */
    private LocalDate calculateEstimatedDeliveryDate() {

        return LocalDate.now()
                .plusDays(ESTIMATED_DELIVERY_DAYS);
    }

    /**
     * Validates order request.
     */
    private void validateOrderRequest(OrderRequest request) {

        if (request == null) {
            throw new BadRequestException(
                    "Order request cannot be null."
            );
        }

        if (request.getAddressId() == null) {
            throw new BadRequestException(
                    "Address id is required."
            );
        }

        if (request.getPaymentMethod() == null) {
            throw new BadRequestException(
                    "Payment method is required."
            );
        }
    }

}