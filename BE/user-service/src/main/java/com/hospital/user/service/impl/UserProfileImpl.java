package com.hospital.user.service.impl;

import com.hospital.user.dto.response.UserProfileResponse;
import com.hospital.user.entity.UserProfile;
import com.hospital.user.kafka.event.UserRegisterEvent;
import com.hospital.user.repository.UserProfileRepo;
import com.hospital.user.service.UserProfileService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

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
}
