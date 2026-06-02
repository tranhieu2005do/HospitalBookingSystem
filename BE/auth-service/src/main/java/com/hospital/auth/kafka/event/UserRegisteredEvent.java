package com.hospital.auth.kafka.event;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserRegisteredEvent {
    private String firebaseUid;
    private String email;
    private String address;
    private String phone;
    private String dateOfBirth;
    private String gender;
    private String fullName;
}
