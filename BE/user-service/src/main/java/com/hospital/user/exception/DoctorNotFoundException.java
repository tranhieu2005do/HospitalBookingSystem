package com.hospital.user.exception;

public class DoctorNotFoundException extends ResourceNotFoundException {
    public DoctorNotFoundException(String message) {
        super(message);
    }
}
