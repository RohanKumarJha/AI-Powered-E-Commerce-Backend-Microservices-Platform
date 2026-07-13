package com.ecommerce.controller;

import com.ecommerce.dto.request.NotificationPreferenceRequest;
import com.ecommerce.dto.response.NotificationPreferenceResponse;
import com.ecommerce.service.NotificationPreferenceService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/notification-preferences")
@RequiredArgsConstructor
public class NotificationPreferenceController {


    private final NotificationPreferenceService preferenceService;




    @PostMapping
    public ResponseEntity<NotificationPreferenceResponse> createPreference(
            @Valid @RequestBody NotificationPreferenceRequest request
    ) {


        return new ResponseEntity<>(
                preferenceService.createPreference(request),
                HttpStatus.CREATED
        );

    }





    @GetMapping("/{userId}")
    public ResponseEntity<NotificationPreferenceResponse> getPreferenceByUserId(
            @PathVariable Long userId
    ) {


        return ResponseEntity.ok(
                preferenceService.getPreferenceByUserId(userId)
        );

    }





    @PutMapping("/{userId}")
    public ResponseEntity<NotificationPreferenceResponse> updatePreference(
            @PathVariable Long userId,
            @Valid @RequestBody NotificationPreferenceRequest request
    ) {


        return ResponseEntity.ok(
                preferenceService.updatePreference(
                        userId,
                        request
                )
        );

    }





    @DeleteMapping("/{userId}")
    public ResponseEntity<Void> deletePreference(
            @PathVariable Long userId
    ) {


        preferenceService.deletePreference(userId);


        return ResponseEntity.noContent().build();

    }

}