package com.hospital.user.controller;

import com.hospital.user.dto.request.UpdateUserProfileRequest;
import com.hospital.user.dto.response.UserProfileResponse;
import com.hospital.user.service.UserProfileService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/users")
@RequiredArgsConstructor
public class UserController {

    private final UserProfileService userProfileService;

    @GetMapping("/{firebaseUid}")
    public ResponseEntity<UserProfileResponse> getUserProfile(@PathVariable String firebaseUid) {
        return ResponseEntity.ok(userProfileService.getUserProfile(firebaseUid));
    }

    @PutMapping("/{firebaseUid}")
    public ResponseEntity<UserProfileResponse> updateUserProfile(
            @PathVariable String firebaseUid,
            @RequestBody UpdateUserProfileRequest request) {
        return ResponseEntity.ok(userProfileService.updateProfile(firebaseUid, request));
    }
}
