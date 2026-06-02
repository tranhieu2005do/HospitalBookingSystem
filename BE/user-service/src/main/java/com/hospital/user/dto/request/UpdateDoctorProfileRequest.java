package com.hospital.user.dto.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UpdateDoctorProfileRequest {
    private String specialization;
    private Integer experienceYears;
    private String bio;
    private String roomId;
}
