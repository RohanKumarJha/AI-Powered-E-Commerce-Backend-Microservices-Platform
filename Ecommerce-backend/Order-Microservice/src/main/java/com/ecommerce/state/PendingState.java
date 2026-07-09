package com.ecommerce.state;

import com.ecommerce.model.ENUM.OrderStatus;
import com.ecommerce.model.Order;
import org.springframework.stereotype.Component;

@Component
public class PendingState implements OrderState {

    @Override
    public void handle(Order order) {
        order.setStatus(OrderStatus.PENDING);
    }
}