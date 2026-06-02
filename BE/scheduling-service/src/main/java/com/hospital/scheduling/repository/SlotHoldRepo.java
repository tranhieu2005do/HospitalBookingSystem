package com.hospital.scheduling.repository;

import com.hospital.scheduling.entity.SlotHold;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SlotHoldRepo extends JpaRepository<SlotHold, Long> {
}
