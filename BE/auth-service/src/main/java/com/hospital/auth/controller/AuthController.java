package com.hospital.auth.controller;

import com.hospital.auth.dto.request.CreateDoctorRequest;
import com.hospital.auth.dto.response.UserResponse;
import com.hospital.auth.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class AuthController {

    private final UserService userService;

    @PostMapping
    public ResponseEntity<UserResponse> createDoctor(
            @Valid @RequestBody CreateDoctorRequest request){
        return ResponseEntity.ok(userService.createDoctor(request));
    }

}
