package com.hospital.booking.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SlotHoldResponse {

    private String holdId;

    private Long slotId;

    private LocalDateTime expiresAt;
}
