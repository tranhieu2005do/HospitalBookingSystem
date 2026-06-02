package com.hospital.auth.dto.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CreateDoctorRequest {

    @NotBlank(message = "Email is required")
    @Email(message = "Invalid email format")
    @JsonProperty("email")
    private String email;

    @JsonProperty("password")
    @NotBlank(message = "Password is required")
    @Size(min = 8, message = "Password must be at least 8 characters long")
    private String password;

    @NotBlank
    @JsonProperty("specialization")
    private String specialization;

    @JsonProperty("years")
    private Integer experience_years;

    @JsonProperty("bio")
    private String bio;

    @NotBlank
    @JsonProperty("room")
    private String room_id;


}
