package com.hospital.scheduling.service.impl;

import com.hospital.scheduling.dto.request.CreatedDoctorScheduleRequest;
import com.hospital.scheduling.dto.response.CreatedDoctorScheduleResponse;
import com.hospital.scheduling.entity.DoctorSchedule;
import com.hospital.scheduling.repository.DoctorScheduleRepo;
import com.hospital.scheduling.service.DoctorScheduleService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class DoctorScheduleImpl implements DoctorScheduleService {

    private final DoctorScheduleRepo  doctorScheduleRepo;

    @Override
    public CreatedDoctorScheduleResponse create(CreatedDoctorScheduleRequest request) {
        log.info("DoctorScheduleImpl create for request {}", request);
        DoctorSchedule doctorSchedule = DoctorSchedule.builder()
                .doctorId(request.getDoctorId())
                .dayOfWeek(request.getDayOfWeek())
                .endTime(request.getEndTime())
                .startTime(request.getStartTime())
                .isActive(true)
                .build();
        doctorScheduleRepo.save(doctorSchedule);
        return null;
    }
}
