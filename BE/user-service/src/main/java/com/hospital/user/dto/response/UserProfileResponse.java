package com.hospital.user.dto.response;

import com.hospital.user.entity.UserProfile;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;

@Builder
@Data
public class UserProfileResponse {

    private String email;
    private String fullName;
    private String address;
    private String phone;
    private LocalDate dateOfBirth;
    private String gender;

    public static UserProfileResponse fromEntity(UserProfile entity) {
        return UserProfileResponse.builder()
                .fullName(entity.getFullName())
                .email(entity.getEmail())
                .address(entity.getAddress())
                .gender(entity.getGender())
                .dateOfBirth(entity.getDateOfBirth())
                .phone(entity.getPhoneNumber())
                .build();
    }
}
