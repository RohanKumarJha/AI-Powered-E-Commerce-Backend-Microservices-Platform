package com.ecommerce.service.impl;

import com.ecommerce.dto.request.NotificationRequest;
import com.ecommerce.dto.request.UpdateNotificationStatusRequest;
import com.ecommerce.dto.response.NotificationResponse;
import com.ecommerce.dto.response.PageResponse;
import com.ecommerce.exception.BadRequestException;
import com.ecommerce.exception.ResourceNotFoundException;
import com.ecommerce.service.factory.NotificationFactory;
import com.ecommerce.mapper.NotificationMapper;
import com.ecommerce.model.Notification;
import com.ecommerce.model.enums.NotificationStatus;
import com.ecommerce.model.enums.NotificationType;
import com.ecommerce.repository.NotificationRepository;
import com.ecommerce.service.NotificationService;
import com.ecommerce.service.strategy.EmailNotificationStrategy;
import com.ecommerce.service.strategy.NotificationStrategy;
import com.ecommerce.service.strategy.PushNotificationStrategy;
import com.ecommerce.service.strategy.SmsNotificationStrategy;
import com.ecommerce.util.PageRequestUtil;
import com.ecommerce.util.PageResponseUtil;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class NotificationServiceImpl implements NotificationService {

    private final NotificationRepository notificationRepository;
    private final NotificationFactory notificationFactory;
    private final NotificationMapper notificationMapper;

    private final Map<NotificationType, NotificationStrategy> strategyMap;

    public NotificationServiceImpl(
            NotificationRepository notificationRepository,
            NotificationFactory notificationFactory,
            NotificationMapper notificationMapper,
            EmailNotificationStrategy emailNotificationStrategy,
            SmsNotificationStrategy smsNotificationStrategy,
            PushNotificationStrategy pushNotificationStrategy
    ) {
        this.notificationRepository = notificationRepository;
        this.notificationFactory = notificationFactory;
        this.notificationMapper = notificationMapper;
        this.strategyMap = Map.of(
                NotificationType.EMAIL,
                emailNotificationStrategy,
                NotificationType.SMS,
                smsNotificationStrategy,
                NotificationType.PUSH,
                pushNotificationStrategy
        );
    }

    @Override
    public NotificationResponse createNotification(
            NotificationRequest request
    ) {
        Notification notification =
                notificationFactory.createNotification(request);
        NotificationStrategy strategy =
                strategyMap.get(
                        notification.getNotificationType()
                );
        if (strategy == null) {
            throw new BadRequestException(
                    "Unsupported notification type: "
                            + notification.getNotificationType()
            );
        }
        boolean sent = strategy.send(notification);
        notification.setStatus(
                sent
                        ? NotificationStatus.SENT
                        : NotificationStatus.FAILED
        );
        Notification saved =
                notificationRepository.save(notification);
        return notificationMapper.toResponse(saved);

    }

    @Override
    public NotificationResponse getNotificationById(
            Long notificationId
    ) {
        Notification notification =
                notificationRepository.findById(notificationId)
                        .orElseThrow(
                                () -> new ResourceNotFoundException(
                                        "Notification not found with id: "
                                                + notificationId
                                )
                        );
        return notificationMapper.toResponse(notification);

    }

    @Override
    public PageResponse<NotificationResponse> getAllNotifications(
            int page,
            int size
    ) {
        Page<Notification> notifications =
                notificationRepository.findAll(
                        PageRequestUtil.create(page, size)
                );
        return PageResponseUtil.convert(
                notifications,
                notificationMapper::toResponse
        );

    }

    @Override
    public PageResponse<NotificationResponse> getNotificationsByUserId(
            Long userId,
            int page,
            int size
    ) {
        Page<Notification> notifications =
                notificationRepository.findByUserId(
                        userId,
                        PageRequestUtil.create(page, size)
                );
        return PageResponseUtil.convert(
                notifications,
                notificationMapper::toResponse
        );

    }

    @Override
    public NotificationResponse updateNotificationStatus(
            Long notificationId,
            UpdateNotificationStatusRequest request
    ) {
        Notification notification =
                notificationRepository.findById(notificationId)
                        .orElseThrow(
                                () -> new ResourceNotFoundException(
                                        "Notification not found with id: "
                                                + notificationId
                                )
                        );
        notification.setStatus(
                request.getStatus()
        );
        notificationFactory.updateNotification(notification);
        Notification updated =
                notificationRepository.save(notification);
        return notificationMapper.toResponse(updated);

    }

    @Override
    public void deleteNotification(Long notificationId) {
        Notification notification = notificationRepository
                .findById(notificationId)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Notification not found with id: " + notificationId
                ));
        if (!notification.getActive()) {
            throw new ResourceNotFoundException(
                    "Notification not found with id: " + notificationId
            );
        }
        notification.setActive(false);
        notificationFactory.updateNotification(notification);
        notificationRepository.save(notification);
    }
}