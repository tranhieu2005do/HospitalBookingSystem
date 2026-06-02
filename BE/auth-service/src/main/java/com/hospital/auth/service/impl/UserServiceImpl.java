package com.hospital.auth.service.impl;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.UserRecord;
import com.hospital.auth.dto.request.CreateDoctorRequest;
import com.hospital.auth.dto.response.UserResponse;
import com.hospital.auth.entity.User;
import com.hospital.auth.entity.enums.Role;
import com.hospital.auth.entity.enums.UserStatus;
import com.hospital.auth.exception.DuplicateUserException;
import com.hospital.auth.exception.NotFoundException;
import com.hospital.auth.exception.UserCreationException;
import com.hospital.auth.repository.UserRepository;
import com.hospital.auth.service.FirebaseService;
import com.hospital.auth.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final FirebaseService firebaseService;

    @Override
    @Transactional
    public UserResponse createDoctor(CreateDoctorRequest request) {
        log.info("Doctor creation started for email: {}", request.getEmail());

        if (userRepository.existsByEmail(request.getEmail())) {
            log.warn("Email {} already exists in local database", request.getEmail());
            throw new DuplicateUserException("User with email " + request.getEmail() + " already exists locally");
        }

        if (firebaseService.getUserByEmail(request.getEmail()).isPresent()) {
            log.warn("Email {} already exists in Firebase", request.getEmail());
            throw new DuplicateUserException("User with email " + request.getEmail() + " already exists in Firebase");
        }

        String firebaseUid = null;
        try {
            firebaseUid = firebaseService.createUser(request.getEmail(), request.getPassword());
            log.info("Firebase user created with UID: {}", firebaseUid);

            firebaseService.setRoleClaim(firebaseUid, Role.DOCTOR);
            log.info("Firebase claims assigned for UID: {}", firebaseUid);

            User user = User.builder()
                    .firebaseUid(firebaseUid)
                    .email(request.getEmail())
                    .role(Role.DOCTOR)
                    .status(UserStatus.ACTIVE)
                    .build();

            User savedUser = userRepository.saveAndFlush(user);
            log.info("Local user persisted with ID: {}", savedUser.getId());

            UserResponse response = UserResponse.builder()
                    .id(savedUser.getId())
                    .firebaseUid(savedUser.getFirebaseUid())
                    .email(savedUser.getEmail())
                    .role(savedUser.getRole())
                    .status(savedUser.getStatus())
                    .build();

            log.info("Doctor creation completed for email: {}", request.getEmail());
            return response;

        } catch (Exception e) {
            log.error("An error occurred during doctor creation for email: {}", request.getEmail(), e);

            if (firebaseUid != null) {
                log.info("Executing compensation logic: Rolling back Firebase user with UID: {}", firebaseUid);
                firebaseService.deleteUser(firebaseUid);
                log.info("Rollback executed for UID: {}", firebaseUid);
            }

            if (e instanceof RuntimeException) {
                throw (RuntimeException) e;
            }
            throw new UserCreationException("Failed to create doctor", e);
        }
    }

    @Override
    public void setRoleAdmin(String firbaseUid, Role role){
        log.info("Role admin started for user with id: {}", firbaseUid);

        // Set claim role in firebase
        firebaseService.setRoleClaim(firbaseUid, role);

        //Set role admin in database
        Optional<User> user =  userRepository.findByFirebaseUid(firbaseUid);
        if(!user.isPresent()){
            log.warn("User with firebase id {} not found", firbaseUid);
            throw new NotFoundException("User with firebase id " + firbaseUid);
        }
        user.get().setRole(role);
        userRepository.save(user.get());
        log.info("Successfully set role admin for user with id: {}", firbaseUid);
    }

    @Override
    public void deleteUser(String firebaseUid) {
        log.info("Deleting user with ID: {}", firebaseUid);

        // delete user in firebase
        firebaseService.deleteUser(firebaseUid);

        //delete user in database
        Optional<User> user =  userRepository.findByFirebaseUid(firebaseUid);
        if(!user.isPresent()){
            log.warn("User with firebase id {} not found in database", firebaseUid);
            throw new NotFoundException("User with firebase id " + firebaseUid);
        }
        userRepository.delete(user.get());
        log.info("Successfully deleted user with ID: {}", firebaseUid);
    }

    @Override
    @Transactional
    public UserResponse registerUser(String email) {
        log.info("Creating user with email: {}", email);
        if (userRepository.existsByEmail(email)) {
            log.warn("Email {} already exists in local database", email);
            throw new DuplicateUserException("User with email " + email + " already exists in locally");
        }

        User newUser = User.builder()
                .email(email)
                .role(Role.PATIENT)
                .status(UserStatus.ACTIVE)
                .build();
        try{
        UserRecord firebaseUser = FirebaseAuth.getInstance().getUserByEmail(email);
        if (firebaseUser == null) {
            log.warn("User with email {} not found in firebase authentication", email);
            throw  new NotFoundException("User with email " + email + " not found in firebase authentication");
        }
        newUser.setFirebaseUid(firebaseUser.getUid());
        userRepository.save(newUser);
        return UserResponse.builder()
                .id(newUser.getId())
                .firebaseUid(firebaseUser.getUid())
                .email(newUser.getEmail())
                .role(newUser.getRole())
                .status(newUser.getStatus())
                .build();
        } catch(Exception e){
            log.info("Executing compensation logic: Rolling back Firebase user email: {}", email);
            firebaseService.deleteUser(email);
            throw new  UserCreationException("Failed to create user", e);
        }
    }

}
