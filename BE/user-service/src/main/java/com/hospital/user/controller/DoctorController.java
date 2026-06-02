package com.hospital.user.controller;

import com.hospital.user.dto.request.UpdateDoctorProfileRequest;
import com.hospital.user.dto.response.DoctorResponse;
import com.hospital.user.service.DoctorProfileService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/doctors")
@RequiredArgsConstructor
public class DoctorController {

    private final DoctorProfileService doctorProfileService;

    @GetMapping("/{firebaseUid}")
    public ResponseEntity<DoctorResponse> getDoctorProfile(@PathVariable String firebaseUid) {
        return ResponseEntity.ok(doctorProfileService.getDoctorProfile(firebaseUid));
    }

    @PutMapping("/{firebaseUid}")
    public ResponseEntity<DoctorResponse> updateDoctorProfile(
            @PathVariable String firebaseUid,
            @RequestBody UpdateDoctorProfileRequest request) {
        return ResponseEntity.ok(doctorProfileService.updateProfile(firebaseUid, request));
    }
}
