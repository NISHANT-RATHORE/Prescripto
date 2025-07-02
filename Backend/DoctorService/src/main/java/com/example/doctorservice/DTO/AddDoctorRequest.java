package com.example.doctorservice.DTO;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Data;
import lombok.experimental.FieldDefaults;

@Builder
@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class AddDoctorRequest {
    String name;
    String email;
    String experience;
    Integer fees;
    String speciality;
    String degree;
    String address1;
    String address2;
    String about;
}