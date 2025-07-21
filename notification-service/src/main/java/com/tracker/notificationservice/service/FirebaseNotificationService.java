package com.tracker.notificationservice.service;

import com.google.firebase.messaging.FirebaseMessaging;
import com.google.firebase.messaging.FirebaseMessagingException;
import com.google.firebase.messaging.Message;
import com.google.firebase.messaging.Notification;
import org.springframework.stereotype.Service;

@Service
public class FirebaseNotificationService implements PushNotificationService {
    private final FirebaseMessaging firebaseMessaging = FirebaseMessaging.getInstance();

    private Message buildMessage(String deviceToken, String title, String body) {
        Notification notification = Notification
                                        .builder()
                                        .setTitle(title)
                                        .setBody(body)
                                        .build();

        return Message
                .builder()
                .setToken(deviceToken)
                .setNotification(notification)
                .build();
    }

    @Override
    public void sendNotification(String deviceToken, String title, String body) throws FirebaseMessagingException {
        Message message = buildMessage(deviceToken, title, body);

        firebaseMessaging.send(message);
    }
}
