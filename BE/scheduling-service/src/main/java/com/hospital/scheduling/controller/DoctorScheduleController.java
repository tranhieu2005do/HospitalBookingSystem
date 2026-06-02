package com.hospital.scheduling.controller;

import com.hospital.scheduling.dto.request.CreatedDoctorScheduleRequest;
import com.hospital.scheduling.dto.request.UpdateScheduleRequest;
import com.hospital.scheduling.dto.response.CreatedDoctorScheduleResponse;
import com.hospital.scheduling.service.DoctorScheduleService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/schedule")
public class DoctorScheduleController {

    private final DoctorScheduleService doctorScheduleService;

    @PostMapping
    public ResponseEntity<CreatedDoctorScheduleResponse> createDoctorSchedule(
            @Valid @RequestBody CreatedDoctorScheduleRequest request
    ){
        return ResponseEntity.ok(doctorScheduleService.create(request));
    }

    // soft delete
    @DeleteMapping("/{scheduleId}")
    public ResponseEntity<Void> deleteDoctorSchedule(
            @PathVariable Long scheduleId
    ){
        doctorScheduleService.cancelDoctorSchedule(scheduleId);
        return ResponseEntity.ok().build();
    }

    @PatchMapping("/{scheduleId}")
    public ResponseEntity<Void> updateSchedule(
            @PathVariable Long scheduleId,
            @Valid @RequestBody UpdateScheduleRequest request
    ){
        doctorScheduleService.updateDoctorSchedule(scheduleId, request);
        return  ResponseEntity.ok().build();
    }
}
