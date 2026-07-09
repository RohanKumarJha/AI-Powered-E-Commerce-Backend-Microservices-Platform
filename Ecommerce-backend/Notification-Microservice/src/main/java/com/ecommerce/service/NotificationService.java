package com.ecommerce.service;

import com.ecommerce.dto.request.NotificationPreferenceRequest;
import com.ecommerce.dto.request.NotificationRequest;
import com.ecommerce.dto.response.NotificationPreferenceResponse;
import com.ecommerce.dto.response.NotificationResponse;

import java.util.List;

public interface NotificationService {


    NotificationResponse createNotification(NotificationRequest request);


    NotificationResponse getNotificationById(Long notificationId);


    List<NotificationResponse> getAllNotifications();


    List<NotificationResponse> getNotificationsByUserId(Long userId);


    NotificationResponse sendNotification(Long notificationId);


    NotificationResponse updateNotificationStatus(
            Long notificationId,
            String status
    );


    NotificationPreferenceResponse createPreference(
            NotificationPreferenceRequest request
    );


    NotificationPreferenceResponse getPreferenceByUserId(
            Long userId
    );


    NotificationPreferenceResponse updatePreference(
            Long userId,
            NotificationPreferenceRequest request
    );

}