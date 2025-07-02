package com.example.authservice.Mapper;

import com.example.authservice.DTO.PatientDTO;
import lombok.experimental.UtilityClass;

import java.util.Date;

@UtilityClass
public class PatientMapper {
    public static PatientDTO toPatientDTO() {
        return PatientDTO.builder()
                .phone("123-456-7890")
                .address1("123 Main St")
                .address2("Apt 4B")
                .image("https://res.cloudinary.com/dctoqfkw0/image/upload/v1/DoctorPhotos/p0ty1a0nli5ory1r7lyg")
                .gender("Male")
                .build();
    }
}
