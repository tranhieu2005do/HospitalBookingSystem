package com.hospital.booking.controller;

import com.hospital.booking.dto.request.CreateBookingRequest;
import com.hospital.booking.dto.response.CreateBookingResponse;
import com.hospital.booking.service.BookingService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/bookings")
@RequiredArgsConstructor
public class BookingController {

    private final BookingService bookingService;

    @PostMapping
    public ResponseEntity<CreateBookingResponse> createBooking(@RequestBody @Valid CreateBookingRequest request) {
        return ResponseEntity.ok(bookingService.createBooking(request));
    }

    @GetMapping("/{id}")
    public ResponseEntity<Object> getBooking(@PathVariable Long id) {
        return ResponseEntity.ok().build();
    }
}
