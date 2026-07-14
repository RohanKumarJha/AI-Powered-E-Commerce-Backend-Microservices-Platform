package com.ecommerce.service.factory;

import com.ecommerce.dto.request.OrderRequest;
import com.ecommerce.exception.BadRequestException;
import com.ecommerce.model.Order;
import com.ecommerce.model.enums.OrderStatus;
import com.ecommerce.model.enums.PaymentMethod;
import com.ecommerce.security.UserContext;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.util.UUID;

@Slf4j
@Component
public class OrderFactory {

    private static final BigDecimal DEFAULT_AMOUNT = BigDecimal.ZERO;
    private static final int ESTIMATED_DELIVERY_DAYS = 5;
    private static final BigDecimal TAX_RATE = new BigDecimal("0.18");
    private static final BigDecimal SHIPPING_CHARGE = new BigDecimal("50");
    private static final BigDecimal FREE_SHIPPING_LIMIT = new BigDecimal("500");

    public Order createOrder(OrderRequest request) {
        log.debug("Creating new order.");
        validateOrderRequest(request);
        Order order = Order.builder()
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
        log.debug(
                "Order created successfully with order number: {}",
                order.getOrderNumber()
        );
        return order;
    }

    public void recalculateOrder(Order order) {
        log.debug(
                "Recalculating totals for order: {}",
                order.getOrderNumber()
        );
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
        log.debug(
                "Order totals recalculated successfully. Total: {}",
                total
        );
    }

    public void updateAddress(
            Order order,
            Long addressId) {
        log.debug("Updating delivery address.");
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
        log.debug("Delivery address updated successfully.");
    }

    public void updatePaymentMethod(
            Order order,
            PaymentMethod paymentMethod) {
        log.debug("Updating payment method.");
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
        log.debug("Payment method updated successfully.");
    }

    public boolean hasItems(Order order) {
        log.debug("Checking whether order contains items.");
        boolean hasItems = order != null
                && order.getOrderItems() != null
                && !order.getOrderItems().isEmpty();
        log.debug("Order contains items: {}", hasItems);
        return hasItems;
    }

    public boolean hasValidTotal(Order order) {
        log.debug("Checking whether order has valid total.");
        boolean valid = order != null
                && order.getTotalAmount() != null
                && order.getTotalAmount()
                .compareTo(BigDecimal.ZERO) > 0;
        log.debug("Order total is valid: {}", valid);
        return valid;
    }

    public void validateBeforeCheckout(Order order) {
        log.debug("Validating order before checkout.");
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
        log.debug(
                "Order [{}] validated successfully.",
                order.getOrderNumber()
        );
    }

    public boolean isEditable(Order order) {
        log.debug("Checking whether order is editable.");
        if (order == null) {
            return false;
        }
        boolean editable = order.getStatus() == OrderStatus.PENDING;
        log.debug("Order editable: {}", editable);
        return editable;
    }

    public void validateEditableOrder(Order order) {
        log.debug("Validating editable order.");
        if (!isEditable(order)) {
            log.warn(
                    "Order [{}] cannot be modified. Current status: {}",
                    order != null ? order.getOrderNumber() : null,
                    order != null ? order.getStatus() : null
            );
            throw new BadRequestException(
                    "Order cannot be modified after confirmation."
            );
        }
        log.debug("Order is editable.");
    }

    private BigDecimal calculateSubtotal(Order order) {
        log.debug("Calculating subtotal.");
        if (order.getOrderItems() == null
                || order.getOrderItems().isEmpty()) {
            log.debug("Order contains no items. Subtotal = 0");
            return BigDecimal.ZERO;
        }
        BigDecimal subtotal = order.getOrderItems()
                .stream()
                .map(item -> item.getLineTotal() == null
                        ? BigDecimal.ZERO
                        : item.getLineTotal())
                .reduce(BigDecimal.ZERO, BigDecimal::add);
        log.debug("Calculated subtotal: {}", subtotal);
        return subtotal;
    }

    private BigDecimal calculateDiscount(Order order) {
        log.debug("Calculating discount.");
        if (order.getOrderItems() == null
                || order.getOrderItems().isEmpty()) {
            log.debug("Order contains no items. Discount = 0");
            return BigDecimal.ZERO;
        }
        BigDecimal discount = order.getOrderItems()
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
        log.debug("Calculated discount: {}", discount);
        return discount;
    }

    private BigDecimal calculateShippingCharge(BigDecimal subtotal) {
        log.debug(
                "Calculating shipping charge for subtotal: {}",
                subtotal
        );
        if (subtotal == null) {
            log.debug(
                    "Subtotal is null. Applying shipping charge: {}",
                    SHIPPING_CHARGE
            );
            return SHIPPING_CHARGE;
        }
        if (subtotal.compareTo(FREE_SHIPPING_LIMIT) >= 0) {
            log.debug("Free shipping applied.");
            return BigDecimal.ZERO;
        }
        log.debug(
                "Shipping charge applied: {}",
                SHIPPING_CHARGE
        );
        return SHIPPING_CHARGE;
    }

    private BigDecimal calculateTax(BigDecimal taxableAmount) {
        log.debug(
                "Calculating tax for taxable amount: {}",
                taxableAmount
        );
        if (taxableAmount == null
                || taxableAmount.compareTo(BigDecimal.ZERO) <= 0) {
            log.debug("Tax = 0");
            return BigDecimal.ZERO;
        }
        BigDecimal tax = taxableAmount
                .multiply(TAX_RATE)
                .setScale(2, RoundingMode.HALF_UP);
        log.debug("Calculated tax: {}", tax);
        return tax;
    }

    private BigDecimal calculateTotalAmount(
            BigDecimal subtotal,
            BigDecimal discount,
            BigDecimal shipping,
            BigDecimal tax) {
        log.debug("Calculating total payable amount.");
        BigDecimal total = subtotal
                .subtract(discount)
                .add(shipping)
                .add(tax)
                .setScale(2, RoundingMode.HALF_UP);
        log.debug("Calculated total amount: {}", total);
        return total;
    }

    private String generateOrderNumber() {
        log.debug("Generating unique order number.");
        String orderNumber = "ORD-"
                + UUID.randomUUID()
                .toString()
                .replace("-", "")
                .substring(0, 10)
                .toUpperCase();
        log.debug("Generated order number: {}", orderNumber);
        return orderNumber;
    }

    private LocalDate calculateEstimatedDeliveryDate() {
        log.debug("Calculating estimated delivery date.");
        LocalDate estimatedDeliveryDate = LocalDate.now()
                .plusDays(ESTIMATED_DELIVERY_DAYS);
        log.debug(
                "Estimated delivery date: {}",
                estimatedDeliveryDate
        );
        return estimatedDeliveryDate;
    }

    private void validateOrderRequest(OrderRequest request) {
        log.debug("Validating order request.");
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
        log.debug("Order request validated successfully.");
    }
}