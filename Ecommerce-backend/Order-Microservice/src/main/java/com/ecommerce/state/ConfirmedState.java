package com.ecommerce.state;

import com.ecommerce.model.Order;
import com.ecommerce.model.ENUM.OrderStatus;
import org.springframework.stereotype.Component;

@Component
public class ConfirmedState implements OrderState {

    @Override
    public void handle(Order order) {
        order.setStatus(OrderStatus.CONFIRMED);
    }
}