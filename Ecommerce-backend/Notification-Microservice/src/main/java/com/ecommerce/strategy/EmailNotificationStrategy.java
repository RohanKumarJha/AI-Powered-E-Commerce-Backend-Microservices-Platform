package com.ecommerce.strategy;

import com.ecommerce.model.Notification;
import org.springframework.stereotype.Component;

@Component("EMAIL")
public class EmailNotificationStrategy implements NotificationStrategy {


    @Override
    public void send(Notification notification) {

        System.out.println(
                "Sending Email Notification: "
                        + notification.getMessage()
        );

    }
}