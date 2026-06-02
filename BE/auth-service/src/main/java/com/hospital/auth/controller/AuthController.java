package com.hospital.auth.controller;

import com.hospital.auth.dto.request.CreateDoctorRequest;
import com.hospital.auth.dto.response.UserResponse;
import com.hospital.auth.entity.enums.Role;
import com.hospital.auth.service.FirebaseService;
import com.hospital.auth.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class AuthController {

    private final UserService userService;
    private final FirebaseService firebaseService;

    @PostMapping
    public ResponseEntity<UserResponse> createDoctor(
            @Valid @RequestBody CreateDoctorRequest request){
        return ResponseEntity.ok(userService.createDoctor(request));
    }

    @PatchMapping("/user/{firebaseUid}")
    public ResponseEntity<Void> setAdmin(
            @PathVariable String firebaseUid,
            @RequestParam Role role
    ){
        userService.setRoleAdmin(firebaseUid, role);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/user/{firebaseUid}")
    public ResponseEntity<Void> deleteUser(
            @PathVariable String firebaseUid
    ){
        userService.deleteUser(firebaseUid);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/register")
    public ResponseEntity<UserResponse> registerUser(
            @RequestParam String email
    ){
        return ResponseEntity.ok(userService.registerUser(email));
    }

}
