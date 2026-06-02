package com.hospital.scheduling.entity;

import com.hospital.scheduling.common.entity.BaseEntity;
import com.hospital.scheduling.enums.SlotStatus;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalTime;

@Entity
@Table(name = "time_slots", uniqueConstraints = {
        @UniqueConstraint(name = "uk_doctor_slot", columnNames = {"doctor_id", "date", "start_time"})
})
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TimeSlot extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "doctor_id", nullable = false, length = 128)
    private Long doctorId;

    @Column(name = "date", nullable = false)
    private LocalDate date;

    @Column(name = "start_time", nullable = false)
    private LocalTime startTime;

    @Column(name = "end_time", nullable = false)
    private LocalTime endTime;

    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false, length = 30)
    private SlotStatus status;

    @Column(name = "appointment_id", length = 128)
    private String appointmentId;

    @Version
    @Column(name = "version", nullable = false)
    private Long version;
}
