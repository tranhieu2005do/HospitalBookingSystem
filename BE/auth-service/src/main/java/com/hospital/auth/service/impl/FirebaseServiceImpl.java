package com.hospital.auth.service.impl;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthException;
import com.google.firebase.auth.UserRecord;
import com.hospital.auth.entity.enums.Role;
import com.hospital.auth.exception.FirebaseClaimUpdateException;
import com.hospital.auth.exception.FirebaseUserCreationException;
import com.hospital.auth.service.FirebaseService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.Optional;

@Slf4j
@Service
public class FirebaseServiceImpl implements FirebaseService {

    @Override
    public String createUser(String email, String password) {
        try {
            UserRecord.CreateRequest request = new UserRecord.CreateRequest()
                    .setEmail(email)
                    .setPassword(password);

            UserRecord userRecord = FirebaseAuth.getInstance().createUser(request);
            log.info("Successfully created new user in Firebase with UID: {}", userRecord.getUid());
            return userRecord.getUid();
        } catch (FirebaseAuthException e) {
            log.error("Failed to create user in Firebase for email: {}", email, e);
            throw new FirebaseUserCreationException("Failed to create Firebase user", e);
        }
    }

    @Override
    public void deleteUser(String firebaseUid) {
        try {
            FirebaseAuth.getInstance().deleteUser(firebaseUid);
            log.info("Successfully deleted user from Firebase with UID: {}", firebaseUid);
        } catch (FirebaseAuthException e) {
            log.error("Failed to delete user in Firebase for UID: {}", firebaseUid, e);
        }
    }

    @Override
    public void setRoleClaim(String firebaseUid, Role role) {
        try {
            Map<String, Object> claims = Map.of("role", role.name());
            FirebaseAuth.getInstance().setCustomUserClaims(firebaseUid, claims);
            log.info("Successfully assigned role {} to Firebase UID: {}", role, firebaseUid);
        } catch (FirebaseAuthException e) {
            log.error("Failed to assign custom claims in Firebase for UID: {}", firebaseUid, e);
            throw new FirebaseClaimUpdateException("Failed to set role claim in Firebase", e);
        }
    }

    @Override
    public Optional<UserRecord> getUserByEmail(String email) {
        try {
            UserRecord userRecord = FirebaseAuth.getInstance().getUserByEmail(email);
            return Optional.of(userRecord);
        } catch (FirebaseAuthException e) {
            return Optional.empty();
        }
    }
}
