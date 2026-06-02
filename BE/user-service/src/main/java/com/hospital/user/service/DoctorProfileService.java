package com.hospital.user.service;

import com.hospital.user.dto.response.DoctorResponse;
import com.hospital.user.kafka.event.CreatedDoctorEvent;

public interface DoctorProfileService {

    DoctorResponse createProfile(CreatedDoctorEvent event);
    DoctorResponse getDoctorProfile(String firebaseUid);
}
