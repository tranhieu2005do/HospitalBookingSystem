package com.hospital.auth.exception;

public class FirebaseUserCreationException extends RuntimeException {
    public FirebaseUserCreationException(String message, Throwable cause) {
        super(message, cause);
    }
}
