package com.hospital.user.kafka.event;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserRegisterEvent {
    private String firebaseUid;
    private String fullName;
    private String email;
    private String address;
    private String phone;
    private LocalDate dateOfBirth;
    private String gender;
}
