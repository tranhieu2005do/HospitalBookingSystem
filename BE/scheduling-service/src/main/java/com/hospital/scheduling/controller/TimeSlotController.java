package com.hospital.scheduling.controller;

import com.hospital.scheduling.service.TimeSlotService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/time-slot")
public class TimeSlotController {

    private final TimeSlotService  timeSlotService;

    @PatchMapping("/id")
    public ResponseEntity<Void> blockTimeSlot(
            @PathVariable Long id
    ){
        timeSlotService.blockTimeSlot(id);
        return ResponseEntity.ok().build();
    }
}
