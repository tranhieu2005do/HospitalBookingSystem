package com.hospital.booking.dto.response;

import com.hospital.booking.enums.BookingStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CreateBookingResponse {

    private Long bookingId;

    private String slotHoldId;

    private BookingStatus status;

    private String message;
}
