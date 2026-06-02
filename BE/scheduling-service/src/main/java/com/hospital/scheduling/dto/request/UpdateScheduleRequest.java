package com.hospital.scheduling.dto.request;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalTime;

@Data
@AllArgsConstructor @NoArgsConstructor
public class UpdateScheduleRequest {

    @NotNull(message = "start time can not be null")
    private LocalTime startTime;

    @NotNull(message = "end time can not be null")
    private LocalTime endTime;
}
