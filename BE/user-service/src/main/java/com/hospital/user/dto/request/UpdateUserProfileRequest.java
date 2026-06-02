package com.hospital.user.dto.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UpdateUserProfileRequest {
    private String fullName;
    private String phoneNumber;
    private LocalDate dateOfBirth;
    private String gender;
    private String address;
}
