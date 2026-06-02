package com.hospital.scheduling.service;

import java.time.LocalDate;
import java.time.LocalTime;

public interface TimeSlotService {

    void genTimeSlot(LocalDate date, LocalTime startTime, LocalTime endTime, String doctorId);

    void reGenTimeSlot(LocalDate date, LocalTime startTime, LocalTime endTime, String doctorId);

    void blockTimeSlot(Long id);

    void pickSlot(Long timeSlotId,  String patientId);
}
