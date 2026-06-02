package com.hospital.user.dto.response;

import com.hospital.user.entity.DoctorProfile;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class DoctorResponse {

    private Long id;

    private String specialization;

    private Integer experienceYears;

    private String bio;

    private String roomId;

    public static DoctorResponse fromEntity(DoctorProfile  doctorProfile) {
        return DoctorResponse.builder()
                .id(doctorProfile.getId())
                .specialization(doctorProfile.getSpecialization())
                .experienceYears(doctorProfile.getExperienceYears())
                .bio(doctorProfile.getBio())
                .roomId(doctorProfile.getRoomId())
                .build();
    }
}
