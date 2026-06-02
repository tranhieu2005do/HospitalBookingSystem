package com.hospital.booking.service;

import org.springframework.http.ResponseEntity;

public interface AppointmentService {
    Object getAppointment(Long id);
    Object updateAppointmentStatus(Long id, Object request);
}
