package com.hospital.scheduling.service.impl;

import com.hospital.scheduling.dto.request.CreatedDoctorScheduleRequest;
import com.hospital.scheduling.dto.request.UpdateScheduleRequest;
import com.hospital.scheduling.dto.response.CreatedDoctorScheduleResponse;
import com.hospital.scheduling.entity.DoctorSchedule;
import com.hospital.scheduling.exception.NotFoundException;
import com.hospital.scheduling.repository.DoctorScheduleRepo;
import com.hospital.scheduling.service.DoctorScheduleService;
import com.hospital.scheduling.service.TimeSlotService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class DoctorScheduleImpl implements DoctorScheduleService {

    private final DoctorScheduleRepo  doctorScheduleRepo;
    private final TimeSlotService timeSlotService;

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

    @Override
    public void cancelDoctorSchedule(Long doctorScheduleId) {
        log.info("DoctorScheduleImpl cancel for doctorSchedule with id {}", doctorScheduleId);
        Optional<DoctorSchedule> schedule = doctorScheduleRepo.findById(doctorScheduleId);
        if(!schedule.isPresent()){
            log.warn("Not found schedule with id {}" , doctorScheduleId);
            throw new NotFoundException("Not found schedule with id " + doctorScheduleId);
        }
        schedule.get().setIsActive(false);
        doctorScheduleRepo.save(schedule.get());
    }

    @Override
    public void updateDoctorSchedule(Long doctorScheduleId, UpdateScheduleRequest request) {
        log.info("Updating schedule with id {}",  doctorScheduleId);
        Optional<DoctorSchedule> schedule = doctorScheduleRepo.findById(doctorScheduleId);
        if(!schedule.isPresent()){
            log.warn("Not found schedule with id {}" , doctorScheduleId);
            throw new NotFoundException("Not found schedule with id " + doctorScheduleId);
        }
        schedule.get().setStartTime(request.getStartTime());
        schedule.get().setEndTime(request.getEndTime());
        schedule.get().setIsActive(true);
        doctorScheduleRepo.save(schedule.get());
    }
}
