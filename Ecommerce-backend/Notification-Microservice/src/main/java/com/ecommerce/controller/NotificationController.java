package com.ecommerce.controller;

import com.ecommerce.dto.request.NotificationPreferenceRequest;
import com.ecommerce.dto.request.NotificationRequest;
import com.ecommerce.dto.response.NotificationPreferenceResponse;
import com.ecommerce.dto.response.NotificationResponse;
import com.ecommerce.service.NotificationService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/notifications")
@RequiredArgsConstructor
public class NotificationController {


    private final NotificationService notificationService;



    @PostMapping
    public ResponseEntity<NotificationResponse> createNotification(
            @Valid @RequestBody NotificationRequest request
    ) {

        return new ResponseEntity<>(
                notificationService.createNotification(request),
                HttpStatus.CREATED
        );
    }



    @GetMapping("/{notificationId}")
    public ResponseEntity<NotificationResponse> getNotificationById(
            @PathVariable Long notificationId
    ) {

        return ResponseEntity.ok(
                notificationService.getNotificationById(notificationId)
        );
    }



    @GetMapping
    public ResponseEntity<List<NotificationResponse>> getAllNotifications() {

        return ResponseEntity.ok(
                notificationService.getAllNotifications()
        );
    }



    @GetMapping("/user/{userId}")
    public ResponseEntity<List<NotificationResponse>> getNotificationsByUserId(
            @PathVariable Long userId
    ) {

        return ResponseEntity.ok(
                notificationService.getNotificationsByUserId(userId)
        );
    }



    @PostMapping("/{notificationId}/send")
    public ResponseEntity<NotificationResponse> sendNotification(
            @PathVariable Long notificationId
    ) {

        return ResponseEntity.ok(
                notificationService.sendNotification(notificationId)
        );
    }



    @PutMapping("/{notificationId}/status")
    public ResponseEntity<NotificationResponse> updateNotificationStatus(
            @PathVariable Long notificationId,
            @RequestParam String status
    ) {

        return ResponseEntity.ok(
                notificationService.updateNotificationStatus(
                        notificationId,
                        status
                )
        );
    }



    @PostMapping("/preferences")
    public ResponseEntity<NotificationPreferenceResponse> createPreference(
            @Valid @RequestBody NotificationPreferenceRequest request
    ) {

        return new ResponseEntity<>(
                notificationService.createPreference(request),
                HttpStatus.CREATED
        );
    }



    @GetMapping("/preferences/{userId}")
    public ResponseEntity<NotificationPreferenceResponse> getPreferenceByUserId(
            @PathVariable Long userId
    ) {

        return ResponseEntity.ok(
                notificationService.getPreferenceByUserId(userId)
        );
    }



    @PutMapping("/preferences/{userId}")
    public ResponseEntity<NotificationPreferenceResponse> updatePreference(
            @PathVariable Long userId,
            @Valid @RequestBody NotificationPreferenceRequest request
    ) {

        return ResponseEntity.ok(
                notificationService.updatePreference(
                        userId,
                        request
                )
        );
    }

}