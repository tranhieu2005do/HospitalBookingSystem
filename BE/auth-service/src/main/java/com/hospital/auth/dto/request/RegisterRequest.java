package com.hospital.auth.dto.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.Column;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data @Builder
@NoArgsConstructor @AllArgsConstructor
public class RegisterRequest {

    @NotBlank(message = "Email can not be blank")
    @Email
    @JsonProperty("email")
    private String email;

    @NotBlank(message = "Your address?")
    @JsonProperty("address")
    private String address;

    @NotBlank(message = "You must enter your name")
    @JsonProperty("full_name")
    private String fullName;

    @JsonProperty("phone_number")
    private String phone;

    @JsonProperty("date_of_birth")
    private LocalDate dateOfBirth;

    @JsonProperty("gender")
    private String gender;

}
