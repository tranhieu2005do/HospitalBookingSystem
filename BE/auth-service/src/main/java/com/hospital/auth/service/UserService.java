package com.hospital.auth.service;

import com.hospital.auth.dto.request.CreateDoctorRequest;
import com.hospital.auth.dto.response.UserResponse;

public interface UserService {
    UserResponse createDoctor(CreateDoctorRequest request);
}
