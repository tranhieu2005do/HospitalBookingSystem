package com.hospital.user.service.impl;

import com.hospital.user.dto.response.DoctorResponse;
import com.hospital.user.entity.DoctorProfile;
import com.hospital.user.kafka.event.CreatedDoctorEvent;
import com.hospital.user.repository.DoctorProfileRepo;
import com.hospital.user.service.DoctorProfileService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class DoctorProfileImpl implements DoctorProfileService {

    private final DoctorProfileRepo  doctorProfileRepo;

    @Override
    public DoctorResponse createProfile(CreatedDoctorEvent event) {
        log.info("DoctorProfileImpl createProfile by event {}", event);
        DoctorProfile  doctorProfile = DoctorProfile.builder()
                .firebaseUid(event.getFirebaseUid())
                .bio(event.getBio())
                .experienceYears(event.getExperience_years())
                .specialization(event.getSpecialization())
                .roomId(event.getRoomId())
                .build();
        doctorProfileRepo.save(doctorProfile);
        return DoctorResponse.fromEntity(doctorProfile);
    }
}
