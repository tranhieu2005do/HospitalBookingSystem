package com.hospital.scheduling.repository;

import com.hospital.scheduling.entity.TimeSlot;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface TimeSlotRepo extends JpaRepository<TimeSlot,Long> {

    List<TimeSlot> findByDoctorIdAndDate(String doctorId, LocalDate date);
}
