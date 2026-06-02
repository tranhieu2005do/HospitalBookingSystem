package com.hospital.scheduling.repository;

import com.hospital.scheduling.entity.DoctorSchedule;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DoctorScheduleRepo extends JpaRepository<DoctorSchedule,Long> {
}
