package com.hospital.auth.kafka.event;

import lombok.Builder;
import lombok.Data;

@Data @Builder
public class CreatedDoctorEvent {

    private String email;
    private String firebaseUid;
    private String specialization;
    private Integer experience_years;
    private String bio;
    private String roomId;
}
