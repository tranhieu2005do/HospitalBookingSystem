package com.hospital.user.controller;

import com.hospital.user.dto.response.UserProfileResponse;
import com.hospital.user.service.UserProfileService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/users")
@RequiredArgsConstructor
public class UserController {

    private final UserProfileService userProfileService;

    @GetMapping("/{firebaseUid}")
    public ResponseEntity<UserProfileResponse> getUserProfile(@PathVariable String firebaseUid) {
        return ResponseEntity.ok(userProfileService.getUserProfile(firebaseUid));
    }
}
