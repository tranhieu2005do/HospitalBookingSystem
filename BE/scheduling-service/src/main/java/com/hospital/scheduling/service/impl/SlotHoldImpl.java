package com.hospital.scheduling.service.impl;

import com.hospital.scheduling.dto.request.CreatedSlotHoldRequest;
import com.hospital.scheduling.entity.SlotHold;
import com.hospital.scheduling.enums.HoldStatus;
import com.hospital.scheduling.repository.SlotHoldRepo;
import com.hospital.scheduling.service.SlotHoldService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class SlotHoldImpl implements SlotHoldService {

    private final SlotHoldRepo  slotHoldRepo;

    @Override
    public void create(CreatedSlotHoldRequest request) {
        log.info("Creating slot hold from request {}", request);
        SlotHold slotHold = SlotHold.builder()
                .slotId(request.getSlotId())
                .patientId(request.getPatientId())
                .expiresAt(request.getExpiresAt())
                .status(HoldStatus.ACTIVE)
                .build();
        slotHoldRepo.save(slotHold);
    }
}
