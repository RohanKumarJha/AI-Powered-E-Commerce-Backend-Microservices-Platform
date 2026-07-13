package com.ecommerce.service.factory;

import com.ecommerce.dto.request.NotificationPreferenceRequest;
import com.ecommerce.exception.BadRequestException;
import com.ecommerce.mapper.NotificationPreferenceMapper;
import com.ecommerce.model.NotificationPreference;
import com.ecommerce.repository.NotificationPreferenceRepository;
import com.ecommerce.security.UserContext;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class NotificationPreferenceFactory {


    private final NotificationPreferenceMapper preferenceMapper;

    private final NotificationPreferenceRepository preferenceRepository;



    public NotificationPreference createPreference(
            NotificationPreferenceRequest request
    ) {


        if (preferenceRepository.existsByUserId(request.getUserId())) {

            throw new BadRequestException(
                    "Notification preference already exists for this user."
            );
        }


        NotificationPreference preference =
                preferenceMapper.toEntity(request);


        preference.setActive(true);

        preference.setCreatedBy(UserContext.getCurrentUserId());

        preference.setUpdatedBy(UserContext.getCurrentUserId());


        return preference;
    }



    public void updatePreference(NotificationPreference preference) {

        preference.setUpdatedBy(UserContext.getCurrentUserId());

    }

}