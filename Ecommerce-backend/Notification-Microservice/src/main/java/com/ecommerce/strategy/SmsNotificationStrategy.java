package com.ecommerce.strategy;

import com.ecommerce.model.Notification;
import org.springframework.stereotype.Component;

@Component("SMS")
public class SmsNotificationStrategy implements NotificationStrategy {


    @Override
    public void send(Notification notification) {

        System.out.println(
                "Sending SMS Notification: "
                        + notification.getMessage()
        );

    }
}