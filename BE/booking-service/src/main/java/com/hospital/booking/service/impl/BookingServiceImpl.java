package com.hospital.booking.service.impl;

import com.hospital.booking.client.ScheduleServiceClient;
import com.hospital.booking.dto.request.CreateBookingRequest;
import com.hospital.booking.dto.response.CreateBookingResponse;
import com.hospital.booking.dto.response.SlotHoldResponse;
import com.hospital.booking.entity.Booking;
import com.hospital.booking.enums.BookingStatus;
import com.hospital.booking.exception.SlotHoldFailedException;
import com.hospital.booking.repository.BookingRepository;
import com.hospital.booking.service.BookingService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;

@Service
@RequiredArgsConstructor
@Slf4j
public class BookingServiceImpl implements BookingService {

    private final BookingRepository bookingRepository;
    private final ScheduleServiceClient scheduleServiceClient;

    @Override
    @Transactional
    public CreateBookingResponse createBooking(CreateBookingRequest request) {
        log.info("Received create booking request: {}", request);

        // Call schedule-service
        log.info("Requesting slot hold from schedule-service for slotId: {}, userId: {}", request.getSlotId(), request.getUserId());
        SlotHoldResponse holdResponse;
        try {
            holdResponse = scheduleServiceClient.holdSlot(request.getSlotId(), request.getUserId());
        } catch (Exception e) {
            log.error("Failed to hold slot via schedule-service", e);
            throw new SlotHoldFailedException("Could not hold the requested slot: " + e.getMessage());
        }

        if (holdResponse == null || holdResponse.getHoldId() == null) {
            log.error("Slot hold response is invalid or empty");
            throw new SlotHoldFailedException("Invalid response from schedule-service");
        }
        log.info("Slot hold successful: {}", holdResponse.getHoldId());

        // Create Booking entity
        Booking booking = Booking.builder()
                .userId(request.getUserId())
                .doctorId(request.getDoctorId())
                .serviceId(request.getServiceId())
                .slotHoldId(holdResponse.getHoldId())
                .status(BookingStatus.WAITING_PAYMENT)
                .totalAmount(BigDecimal.ZERO) // Based on requirement, total amount is required in entity but not in request. Setting ZERO as default for now, could be fetched from service if a Service entity existed.
                .build();

        bookingRepository.save(booking);
        log.info("Booking persisted successfully with ID: {}", booking.getId());

        log.info("Create booking completed successfully");
        return CreateBookingResponse.builder()
                .bookingId(booking.getId())
                .slotHoldId(holdResponse.getHoldId())
                .status(BookingStatus.WAITING_PAYMENT)
                .message("Booking created successfully. Waiting for payment.")
                .build();
    }

    @Override
    public Object getBooking(Long id) {
        return null;
    }
}
