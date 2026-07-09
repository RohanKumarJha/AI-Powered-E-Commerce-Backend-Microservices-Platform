package com.ecommerce.adapter;

import org.springframework.stereotype.Component;

@Component
public class MockNotificationProviderAdapter implements NotificationProviderAdapter {


    @Override
    public boolean sendEmail(String recipient, String message) {

        System.out.println(
                "Mock Email sent to: "
                        + recipient
        );

        System.out.println(
                "Message: "
                        + message
        );

        return true;
    }


    @Override
    public boolean sendSms(String recipient, String message) {

        System.out.println(
                "Mock SMS sent to: "
                        + recipient
        );

        System.out.println(
                "Message: "
                        + message
        );

        return true;
    }


    @Override
    public boolean sendPush(String recipient, String message) {

        System.out.println(
                "Mock Push Notification sent to: "
                        + recipient
        );

        System.out.println(
                "Message: "
                        + message
        );

        return true;
    }

}