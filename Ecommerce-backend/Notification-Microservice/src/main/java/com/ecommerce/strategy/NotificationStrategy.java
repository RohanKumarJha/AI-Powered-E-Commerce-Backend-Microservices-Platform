package com.ecommerce.strategy;

import com.ecommerce.model.Notification;

public interface NotificationStrategy {

    void send(Notification notification);

}