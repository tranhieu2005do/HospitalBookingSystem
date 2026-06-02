package com.hospital.scheduling.service;

import com.hospital.scheduling.dto.request.CreatedSlotHoldRequest;

public interface SlotHoldService {

    void create(CreatedSlotHoldRequest request);
}
