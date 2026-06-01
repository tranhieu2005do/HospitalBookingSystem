package com.hospital.gateway.config;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

import jakarta.annotation.PostConstruct;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

@Configuration
public class FirebaseConfig {

    @Value("${firebase.service-account.path:classpath:firebase-service-account.json}")
    private String serviceAccountPath;

    @PostConstruct
    public void init() throws IOException {
        if (FirebaseApp.getApps().isEmpty()) {
            try (InputStream serviceAccount = getServiceAccountStream()) {
                FirebaseOptions options = FirebaseOptions.builder()
                        .setCredentials(GoogleCredentials.fromStream(serviceAccount))
                        .build();

                FirebaseApp.initializeApp(options);
            } catch (Exception e) {
                // In a production scenario, you might want to fail fast or log an error.
                // For local development, we catch and log in case the file is missing but Firebase is bypassed.
                System.err.println("Failed to initialize Firebase: " + e.getMessage());
            }
        }
    }

    private InputStream getServiceAccountStream() throws IOException {
        if (serviceAccountPath.startsWith("classpath:")) {
            String path = serviceAccountPath.substring("classpath:".length());
            InputStream is = getClass().getClassLoader().getResourceAsStream(path);
            if (is == null) {
                throw new IOException("Firebase service account file not found in classpath: " + path);
            }
            return is;
        } else {
            return new FileInputStream(serviceAccountPath);
        }
    }
}
