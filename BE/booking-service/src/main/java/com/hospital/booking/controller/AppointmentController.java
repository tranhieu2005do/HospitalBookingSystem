package com.hospital.booking.controller;

import com.hospital.booking.service.AppointmentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/appointments")
@RequiredArgsConstructor
public class AppointmentController {

    private final AppointmentService appointmentService;

    @GetMapping("/{id}")
    public ResponseEntity<Object> getAppointment(@PathVariable Long id) {
        return ResponseEntity.ok().build();
    }

    @PutMapping("/{id}/status")
    public ResponseEntity<Object> updateAppointmentStatus(@PathVariable Long id, @RequestBody Object request) {
        return ResponseEntity.ok().build();
    }
}
