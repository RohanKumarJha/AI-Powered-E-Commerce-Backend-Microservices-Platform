package com.ecommerce.service.adapter;

import org.springframework.stereotype.Component;

@Component
public class MockNotificationProviderAdapter
        implements NotificationProviderAdapter {


    @Override
    public boolean sendEmail(
            String recipient,
            String subject,
            String message
    ) {

        System.out.println(
                "Mock Email Sent To: " + recipient
        );

        System.out.println(
                "Subject: " + subject
        );

        System.out.println(
                "Message: " + message
        );


        return true;
    }



    @Override
    public boolean sendSms(
            String recipient,
            String message
    ) {

        System.out.println(
                "Mock SMS Sent To: " + recipient
        );

        System.out.println(
                "Message: " + message
        );


        return true;
    }



    @Override
    public boolean sendPush(
            String recipient,
            String message
    ) {

        System.out.println(
                "Mock Push Notification Sent To: " + recipient
        );

        System.out.println(
                "Message: " + message
        );


        return true;
    }

}