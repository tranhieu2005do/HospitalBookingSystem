package com.hospital.user.service.impl;

import com.hospital.user.dto.response.UserProfileResponse;
import com.hospital.user.entity.UserProfile;
import com.hospital.user.kafka.event.UserRegisterEvent;
import com.hospital.user.repository.UserProfileRepo;
import com.hospital.user.service.UserProfileService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserProfileImpl implements UserProfileService {

    private final UserProfileRepo userProfileRepo;

    @Override
    public UserProfileResponse createProfile(UserRegisterEvent event) {
        log.info("UserProfileImpl createProfile by event {}", event);
        UserProfile newProfile = UserProfile.builder()
                .address(event.getAddress())
                .email(event.getEmail())
                .dateOfBirth(event.getDateOfBirth())
                .firebaseUid(event.getFirebaseUid())
                .gender(event.getGender())
                .phoneNumber(event.getPhone())
                .fullName(event.getFullName())
                .build();
        userProfileRepo.save(newProfile);
        return UserProfileResponse.fromEntity(newProfile);
    }

    @Override
    @Transactional(readOnly = true)
    public UserProfileResponse getUserProfile(String firebaseUid) {
        UserProfile userProfile = userProfileRepo.findByFirebaseUid(firebaseUid)
                .orElseThrow(() -> new com.hospital.user.exception.UserNotFoundException("User profile not found for firebaseUid: " + firebaseUid));

        return UserProfileResponse.fromEntity(userProfile);
    }

    @Override
    @Transactional
    public UserProfileResponse updateProfile(String firebaseUid, com.hospital.user.dto.request.UpdateUserProfileRequest request) {
        UserProfile userProfile = userProfileRepo.findByFirebaseUid(firebaseUid)
                .orElseThrow(() -> new com.hospital.user.exception.UserNotFoundException("User profile not found for firebaseUid: " + firebaseUid));

        if (request.getFullName() != null) userProfile.setFullName(request.getFullName());
        if (request.getPhoneNumber() != null) userProfile.setPhoneNumber(request.getPhoneNumber());
        if (request.getDateOfBirth() != null) userProfile.setDateOfBirth(request.getDateOfBirth());
        if (request.getGender() != null) userProfile.setGender(request.getGender());
        if (request.getAddress() != null) userProfile.setAddress(request.getAddress());

        userProfileRepo.save(userProfile);
        return UserProfileResponse.fromEntity(userProfile);
    }
}
