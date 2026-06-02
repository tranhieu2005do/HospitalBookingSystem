package com.hospital.auth.service;

import com.hospital.auth.dto.request.CreateDoctorRequest;
import com.hospital.auth.dto.request.RegisterRequest;
import com.hospital.auth.dto.response.UserResponse;
import com.hospital.auth.entity.enums.Role;

public interface UserService {
    UserResponse createDoctor(CreateDoctorRequest request);

    void setRoleAdmin(String firebaseUid, Role role);

    void deleteUser(String firebaseUid);

    UserResponse registerUser(RegisterRequest request);
}
