package com.ecommerce.service.impl;

import com.ecommerce.dto.request.NotificationPreferenceRequest;
import com.ecommerce.dto.request.NotificationRequest;
import com.ecommerce.dto.response.NotificationPreferenceResponse;
import com.ecommerce.dto.response.NotificationResponse;
import com.ecommerce.service.NotificationService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class NotificationServiceImpl implements NotificationService {
    @Override
    public NotificationResponse createNotification(NotificationRequest request) {
        return null;
    }

    @Override
    public NotificationResponse getNotificationById(Long notificationId) {
        return null;
    }

    @Override
    public List<NotificationResponse> getAllNotifications() {
        return List.of();
    }

    @Override
    public List<NotificationResponse> getNotificationsByUserId(Long userId) {
        return List.of();
    }

    @Override
    public NotificationResponse sendNotification(Long notificationId) {
        return null;
    }

    @Override
    public NotificationResponse updateNotificationStatus(Long notificationId, String status) {
        return null;
    }

    @Override
    public NotificationPreferenceResponse createPreference(NotificationPreferenceRequest request) {
        return null;
    }

    @Override
    public NotificationPreferenceResponse getPreferenceByUserId(Long userId) {
        return null;
    }

    @Override
    public NotificationPreferenceResponse updatePreference(Long userId, NotificationPreferenceRequest request) {
        return null;
    }
}
