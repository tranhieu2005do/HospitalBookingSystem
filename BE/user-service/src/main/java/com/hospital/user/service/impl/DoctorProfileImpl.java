package com.hospital.user.service.impl;

import com.hospital.user.dto.response.DoctorResponse;
import com.hospital.user.entity.DoctorProfile;
import com.hospital.user.kafka.event.CreatedDoctorEvent;
import com.hospital.user.repository.DoctorProfileRepo;
import com.hospital.user.service.DoctorProfileService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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

    @Override
    @Transactional(readOnly = true)
    public DoctorResponse getDoctorProfile(String firebaseUid) {
        DoctorProfile doctorProfile = doctorProfileRepo.findByFirebaseUid(firebaseUid)
                .orElseThrow(() -> new com.hospital.user.exception.DoctorNotFoundException("Doctor profile not found for firebaseUid: " + firebaseUid));

        return DoctorResponse.fromEntity(doctorProfile);
    }

    @Override
    @Transactional
    public DoctorResponse updateProfile(String firebaseUid, com.hospital.user.dto.request.UpdateDoctorProfileRequest request) {
        DoctorProfile doctorProfile = doctorProfileRepo.findByFirebaseUid(firebaseUid)
                .orElseThrow(() -> new com.hospital.user.exception.DoctorNotFoundException("Doctor profile not found for firebaseUid: " + firebaseUid));

        if (request.getSpecialization() != null) doctorProfile.setSpecialization(request.getSpecialization());
        if (request.getExperienceYears() != null) doctorProfile.setExperienceYears(request.getExperienceYears());
        if (request.getBio() != null) doctorProfile.setBio(request.getBio());
        if (request.getRoomId() != null) doctorProfile.setRoomId(request.getRoomId());

        doctorProfileRepo.save(doctorProfile);
        return DoctorResponse.fromEntity(doctorProfile);
    }
}
