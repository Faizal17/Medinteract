package com.csci5308.medinteract.notification.service;

import com.csci5308.medinteract.notification.model.NotificationModel;
import com.csci5308.medinteract.notification.repository.NotificationRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@SpringBootTest
public class NotificationServiceTest {

    @Mock
    private NotificationRepository notificationRepository;

    @InjectMocks
    private NotificationServiceImpl notificationService;

    @Test
    public void saveNotificationTest() {
        NotificationModel notificationModel = new NotificationModel(
                "Doctor", 123L, "New message",
                "Notification", "http://localhost", "success",
                LocalDateTime.now()
        );
        when(notificationRepository.save(any())).thenReturn(notificationModel);
        NotificationModel savedNotificationModel = notificationService.saveNotification(notificationModel);
        Assertions.assertEquals(notificationModel, savedNotificationModel);
    }

    @Test
    public void fetchAllTest() {
        List<NotificationModel> notificationModelList = new ArrayList<>();
        notificationModelList.add(new NotificationModel(
                "Doctor", 123L, "New message",
                "Notification", "http://localhost", "success",
                LocalDateTime.now()
        ));
        notificationModelList.add(new NotificationModel(
                "Doctor", 456L, "New message",
                "Notification", "http://localhost", "success",
                LocalDateTime.now()
        ));
        when(notificationRepository.findAllByUserIdAndAndUserTypeOrderByNotificationDateTimeDesc(any(), any())).thenReturn(notificationModelList);
        List<NotificationModel> fetchedNotificationModelList = notificationService.fetchAll(123L, "Doctor");
        Assertions.assertEquals(notificationModelList, fetchedNotificationModelList);
    }
}

