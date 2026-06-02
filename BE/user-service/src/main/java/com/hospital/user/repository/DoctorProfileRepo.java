package com.hospital.user.repository;

import com.hospital.user.entity.DoctorProfile;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DoctorProfileRepo extends JpaRepository<DoctorProfile,Long> {
}
