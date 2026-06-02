package com.hospital.auth.service;

import com.google.firebase.auth.UserRecord;
import com.hospital.auth.entity.enums.Role;

import java.util.Optional;

public interface FirebaseService {

    String createUser(String email, String password);

    void deleteUser(String firebaseUid);

    void setRoleClaim(String firebaseUid, Role role);

    Optional<UserRecord> getUserByEmail(String email);
}
