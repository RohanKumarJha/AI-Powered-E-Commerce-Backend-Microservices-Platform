package com.ecommerce.service.state;

import com.ecommerce.model.Order;
import com.ecommerce.model.ENUM.OrderStatus;
import org.springframework.stereotype.Component;

@Component
public class CancelledState implements OrderState {

    @Override
    public void handle(Order order) {
        order.setStatus(OrderStatus.CANCELLED);
    }
}