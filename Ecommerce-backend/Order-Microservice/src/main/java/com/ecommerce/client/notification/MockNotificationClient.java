package com.ecommerce.client.notification;

import org.springframework.stereotype.Service;

@Service
public class MockNotificationClient implements NotificationClient {

    @Override
    public void sendOrderPlacedNotification(Long userId, Long orderId) {
        // Mock implementation
        System.out.println("Order Placed with Order Id "+ orderId);
    }
}