package com.hospital.scheduling.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.time.LocalTime;

public class CreatedDoctorScheduleResponse {

    @JsonProperty("id")
    private Long id;

    @JsonProperty("doctor_id")
    private Long doctorId;

    @JsonProperty("day_week")
    private String dayWeek;

    @JsonProperty("start_time")
    private LocalTime startTime;

    @JsonProperty("end_time")
    private LocalTime endTime;
}
