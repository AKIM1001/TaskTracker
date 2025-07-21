package com.tracker.notificationservice.config;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;

import java.io.IOException;
import java.io.InputStream;

@Configuration
public class FirebaseConfiguration {
    @Value("${firebase.service.account-file}")
    private String firebaseConfigFilePath;

    @PostConstruct
    public void initialize() throws IOException {
        InputStream inputStream = new ClassPathResource(firebaseConfigFilePath).getInputStream();

        FirebaseOptions options = FirebaseOptions.builder()
                .setCredentials(GoogleCredentials.fromStream(inputStream))
                .build();

        if (FirebaseApp.getApps().isEmpty())
            FirebaseApp.initializeApp(options);
    }
}
