package com.tracker.notificationservice.service;

public interface PushNotificationService {
    void sendNotification(String deviceToken, String title, String body) throws Exception;
}
