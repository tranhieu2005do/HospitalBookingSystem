package com.hospital.scheduling.dto.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor @NoArgsConstructor
public class CreatedSlotHoldRequest {

    private Long slotId;

    private String patientId;

    private LocalDateTime expiresAt;
}
