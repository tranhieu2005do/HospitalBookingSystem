package com.hospital.user.kafka.consumer;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.hospital.user.kafka.event.CreatedDoctorEvent;
import com.hospital.user.kafka.event.UserRegisterEvent;
import com.hospital.user.service.DoctorProfileService;
import com.hospital.user.service.UserProfileService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
@Slf4j
@RequiredArgsConstructor
public class UserServiceConsumer {

    private final ObjectMapper objectMapper;
    private final UserProfileService userProfileService;
    private final DoctorProfileService doctorProfileService;

    @KafkaListener(topics = "user-created-topic")
    public void consumeUserCreated(String message) {
        log.info("Received user-created-topic: " + message);
        try {
            UserRegisterEvent event =
                    objectMapper.readValue(
                            message,
                            UserRegisterEvent.class
                    );

            log.info("firbaseUid = {}", event.getFirebaseUid());
            log.info("name = {}", event.getFullName());
            userProfileService.createProfile(event);

        } catch (Exception e) {
            log.error("Parse failed", e);
        }
    }

    @KafkaListener(topics = "doctor-created-topic")
    public void consumeDoctorCreated(String message) {
        log.info("Receive doctor-created-topic: " + message);
        try {
            CreatedDoctorEvent event =
                    objectMapper.readValue(
                            message,
                            CreatedDoctorEvent.class
                    );

            log.info("firbaseUid of doctor = {}", event.getFirebaseUid());
            log.info("email = {}", event.getEmail());
            doctorProfileService.createProfile(event);

        } catch (Exception e) {
            log.error("Parse failed", e);
        }
    }
}
