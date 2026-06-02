package com.hospital.auth.dto.response;

import com.hospital.auth.entity.enums.Role;
import com.hospital.auth.entity.enums.UserStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserResponse {
    private Long id;
    private String firebaseUid;
    private String email;
    private Role role;
    private UserStatus status;
}
