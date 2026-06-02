package com.hospital.booking.service;

import com.hospital.booking.dto.request.CreateBookingRequest;
import com.hospital.booking.dto.response.CreateBookingResponse;

public interface BookingService {
    CreateBookingResponse createBooking(CreateBookingRequest request);
    Object getBooking(Long id);
}
