package com.hospital.auth.kafka.producer;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.hospital.auth.kafka.event.UserRegisteredEvent;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserEventProducer {

    private final KafkaTemplate<String, String> kafkaTemplate;
    private final ObjectMapper objectMapper;

    public void publish(
            String topic,
            String key,
            Object event) {

        try {
            log.info("Publishing event to kafka topic: {} with event {}", topic, event);
            String payload = objectMapper.writeValueAsString(event);

            kafkaTemplate.send(topic, key, payload);

            log.info(
                    "Published event to topic={}, key={}",
                    topic,
                    key);

        } catch (Exception e) {

            log.error("Failed to publish kafka event", e);

            throw new RuntimeException(e);
        }
    }
}
