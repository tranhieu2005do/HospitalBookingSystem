package com.hospital.user.controller;

import com.hospital.user.dto.response.DoctorResponse;
import com.hospital.user.service.DoctorProfileService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/doctors")
@RequiredArgsConstructor
public class DoctorController {

    private final DoctorProfileService doctorProfileService;

    @GetMapping("/{firebaseUid}")
    public ResponseEntity<DoctorResponse> getDoctorProfile(@PathVariable String firebaseUid) {
        return ResponseEntity.ok(doctorProfileService.getDoctorProfile(firebaseUid));
    }
}
