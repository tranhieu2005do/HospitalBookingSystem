package com.hospital.scheduling.controller;

import com.hospital.scheduling.service.TimeSlotService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/time-slot")
public class TimeSlotController {

    private final TimeSlotService  timeSlotService;

    @PatchMapping("/block")
    public ResponseEntity<Void> blockTimeSlot(
            @RequestParam Long id
    ){
        timeSlotService.blockTimeSlot(id);
        return ResponseEntity.ok().build();
    }

    @PatchMapping("/pick")
    public ResponseEntity<Void> pickTimeSlot(
            @RequestParam Long id
    ){
        Long patientId = null;
        timeSlotService.pickSlot(id, patientId);
        return ResponseEntity.ok().build();
    }
}
