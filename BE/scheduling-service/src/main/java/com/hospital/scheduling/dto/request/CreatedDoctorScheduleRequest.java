package com.hospital.scheduling.dto.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalTime;

@Data
@AllArgsConstructor @NoArgsConstructor
public class CreatedDoctorScheduleRequest {

    @JsonProperty("doctor_id")
    private String doctorId;

    @JsonProperty("day_of_week")
    private String dayOfWeek;

    @JsonProperty("start_time")
    private LocalTime startTime;

    @JsonProperty("end_time")
    private LocalTime endTime;
}
