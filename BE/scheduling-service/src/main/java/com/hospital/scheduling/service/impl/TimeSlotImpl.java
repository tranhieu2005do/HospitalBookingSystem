package com.hospital.scheduling.service.impl;

import com.hospital.scheduling.entity.TimeSlot;
import com.hospital.scheduling.enums.SlotStatus;
import com.hospital.scheduling.exception.NotFoundException;
import com.hospital.scheduling.repository.TimeSlotRepo;
import com.hospital.scheduling.service.TimeSlotService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class TimeSlotImpl implements TimeSlotService {

    private final TimeSlotRepo  timeSlotRepo;

    @Override
    public void genTimeSlot(LocalDate date, LocalTime startTime, LocalTime endTime, Long doctorId) {

        log.info("Generating slots for doctorId={}, date={}", doctorId, date);

        int SLOT_DURATION = 30;
        List<TimeSlot> slots = new ArrayList<>();

        LocalTime current = startTime;

        while (current != null) {

            if (current.plusMinutes(SLOT_DURATION).isAfter(endTime)) {
                break;
            }

            LocalTime slotEnd = current.plusMinutes(SLOT_DURATION);

            slots.add(TimeSlot.builder()
                    .doctorId(doctorId)
                    .date(date)
                    .startTime(current)
                    .endTime(slotEnd)
                    .status(SlotStatus.AVAILABLE)
                    .build());

            current = slotEnd;

            // CASE 2: nếu tới 11:30 → nhảy sang 14:00
            if (current.equals(LocalTime.of(11, 30))) {
                current = LocalTime.of(14, 0);
            }
        }

        timeSlotRepo.saveAll(slots);

        log.info("Generated {} slots", slots.size());
    }

    @Override
    public void reGenTimeSlot(LocalDate date, LocalTime startTime, LocalTime endTime, Long doctorId) {
        log.info("Regenerating slots for doctorId={}, date={}", doctorId, date);
        List<TimeSlot> slots = timeSlotRepo.findByDoctorIdAndDate(doctorId, date);
        List<TimeSlot> filtered = slots.stream()
                .filter(s ->
                        s.getStartTime().isBefore(endTime) &&
                                s.getEndTime().isAfter(startTime)
                )
                .toList();
        for(TimeSlot slot : filtered){
            if(!slots.contains(slot)){
                slot.setStatus(SlotStatus.CANCELLED);
                timeSlotRepo.save(slot);
            }
            slot.setVersion(2L);
        }
        timeSlotRepo.saveAll(filtered);
    }

    @Override
    public void blockTimeSlot(Long id) {
        log.info("Blocking slots with id={}", id);
        Optional<TimeSlot> optional = timeSlotRepo.findById(id);
        if(!optional.isPresent()){
            log.error("TimeSlot with id={} not found", id);
            throw new NotFoundException("TimeSlot with id=" + id + " not found");
        }
        optional.get().setStatus(SlotStatus.BLOCKED);
        timeSlotRepo.save(optional.get());
    }

    @Override
    public void pickSlot(Long timeSlotId) {
        log.info("Picking slot with id={}", timeSlotId);
        Optional<TimeSlot> optional = timeSlotRepo.findById(timeSlotId);
        if(!optional.isPresent()){
            log.error("TimeSlot with id={} not found", timeSlotId);
            throw new NotFoundException("TimeSlot with id=" + timeSlotId + " not found");
        }
        optional.get().setStatus(SlotStatus.HOLD);
        timeSlotRepo.save(optional.get());
    }


}
