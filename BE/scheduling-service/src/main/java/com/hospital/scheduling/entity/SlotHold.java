package com.hospital.scheduling.entity;

import com.hospital.scheduling.common.entity.BaseEntity;
import com.hospital.scheduling.enums.HoldStatus;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "slot_holds")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SlotHold extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "slot_id", nullable = false)
    private Long slotId;

    @Column(name = "patient_id", nullable = false, length = 128)
    private String patientId;

    @Column(name = "expires_at", nullable = false)
    private LocalDateTime expiresAt;

    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false, length = 30)
    private HoldStatus status;
}
