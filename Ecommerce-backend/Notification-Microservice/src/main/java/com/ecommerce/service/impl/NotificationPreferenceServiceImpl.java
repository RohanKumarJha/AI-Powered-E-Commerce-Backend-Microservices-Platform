package com.ecommerce.service.impl;

import com.ecommerce.dto.request.NotificationPreferenceRequest;
import com.ecommerce.dto.response.NotificationPreferenceResponse;
import com.ecommerce.exception.ResourceNotFoundException;
import com.ecommerce.service.factory.NotificationPreferenceFactory;
import com.ecommerce.mapper.NotificationPreferenceMapper;
import com.ecommerce.model.NotificationPreference;
import com.ecommerce.repository.NotificationPreferenceRepository;
import com.ecommerce.service.NotificationPreferenceService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class NotificationPreferenceServiceImpl
        implements NotificationPreferenceService {


    private final NotificationPreferenceRepository preferenceRepository;

    private final NotificationPreferenceFactory preferenceFactory;

    private final NotificationPreferenceMapper preferenceMapper;



    @Override
    public NotificationPreferenceResponse createPreference(
            NotificationPreferenceRequest request
    ) {


        NotificationPreference preference =
                preferenceFactory.createPreference(request);


        NotificationPreference saved =
                preferenceRepository.save(preference);


        return preferenceMapper.toResponse(saved);
    }





    @Override
    public NotificationPreferenceResponse getPreferenceByUserId(
            Long userId
    ) {


        NotificationPreference preference =
                preferenceRepository.findByUserId(userId)
                        .orElseThrow(
                                () -> new ResourceNotFoundException(
                                        "Notification preference not found for user: "
                                                + userId
                                )
                        );


        return preferenceMapper.toResponse(preference);

    }





    @Override
    public NotificationPreferenceResponse updatePreference(
            Long userId,
            NotificationPreferenceRequest request
    ) {


        NotificationPreference preference =
                preferenceRepository.findByUserId(userId)
                        .orElseThrow(
                                () -> new ResourceNotFoundException(
                                        "Notification preference not found for user: "
                                                + userId
                                )
                        );


        preference.setEmailEnabled(
                request.getEmailEnabled()
        );


        preference.setSmsEnabled(
                request.getSmsEnabled()
        );


        preference.setPushEnabled(
                request.getPushEnabled()
        );


        preferenceFactory.updatePreference(preference);


        NotificationPreference updated =
                preferenceRepository.save(preference);


        return preferenceMapper.toResponse(updated);
    }





    @Override
    public void deletePreference(
            Long userId
    ) {


        NotificationPreference preference =
                preferenceRepository.findByUserId(userId)
                        .orElseThrow(
                                () -> new ResourceNotFoundException(
                                        "Notification preference not found for user: "
                                                + userId
                                )
                        );


        preference.setActive(false);


        preferenceFactory.updatePreference(preference);


        preferenceRepository.save(preference);

    }

}