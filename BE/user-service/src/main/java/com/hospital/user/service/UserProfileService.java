package com.hospital.user.service;

import com.hospital.user.dto.response.UserProfileResponse;
import com.hospital.user.kafka.event.UserRegisterEvent;

public interface UserProfileService {

    UserProfileResponse createProfile(UserRegisterEvent event);
    UserProfileResponse getUserProfile(String firebaseUid);
}
