package com.ecommerce.controller;

import com.ecommerce.dto.request.NotificationRequest;
import com.ecommerce.dto.request.UpdateNotificationStatusRequest;
import com.ecommerce.dto.response.NotificationResponse;
import com.ecommerce.dto.response.PageResponse;
import com.ecommerce.service.NotificationService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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
    public ResponseEntity<PageResponse<NotificationResponse>> getAllNotifications(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size
    ) {


        return ResponseEntity.ok(
                notificationService.getAllNotifications(
                        page,
                        size
                )
        );

    }





    @GetMapping("/user/{userId}")
    public ResponseEntity<PageResponse<NotificationResponse>> getNotificationsByUserId(
            @PathVariable Long userId,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size
    ) {


        return ResponseEntity.ok(
                notificationService.getNotificationsByUserId(
                        userId,
                        page,
                        size
                )
        );

    }





    @PatchMapping("/{notificationId}/status")
    public ResponseEntity<NotificationResponse> updateNotificationStatus(
            @PathVariable Long notificationId,
            @Valid @RequestBody UpdateNotificationStatusRequest request
    ) {


        return ResponseEntity.ok(
                notificationService.updateNotificationStatus(
                        notificationId,
                        request
                )
        );

    }





    @DeleteMapping("/{notificationId}")
    public ResponseEntity<Void> deleteNotification(
            @PathVariable Long notificationId
    ) {


        notificationService.deleteNotification(notificationId);


        return ResponseEntity.noContent().build();

    }

}