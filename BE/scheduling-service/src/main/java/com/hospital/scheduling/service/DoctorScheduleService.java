package com.hospital.scheduling.service;

import com.hospital.scheduling.dto.request.CreatedDoctorScheduleRequest;
import com.hospital.scheduling.dto.request.UpdateScheduleRequest;
import com.hospital.scheduling.dto.response.CreatedDoctorScheduleResponse;

public interface DoctorScheduleService {

    CreatedDoctorScheduleResponse create(CreatedDoctorScheduleRequest request);

    void cancelDoctorSchedule(Long doctorScheduleId);

    void updateDoctorSchedule(Long doctorScheduleId, UpdateScheduleRequest request);
}
