package com.ecommerce.strategy;

import com.ecommerce.model.Notification;
import org.springframework.stereotype.Component;

@Component("PUSH")
public class PushNotificationStrategy implements NotificationStrategy {


    @Override
    public void send(Notification notification) {

        System.out.println(
                "Sending Push Notification: "
                        + notification.getMessage()
        );

    }
}