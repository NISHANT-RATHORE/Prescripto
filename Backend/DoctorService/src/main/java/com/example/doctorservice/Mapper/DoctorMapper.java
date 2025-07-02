package com.example.doctorservice.Mapper;

import com.example.doctorservice.DTO.AddDoctorRequest;
import com.example.doctorservice.Model.Doctor;
import lombok.experimental.UtilityClass;

@UtilityClass
public class DoctorMapper {
    public static Doctor mapToDoctor(AddDoctorRequest request) {
        return Doctor.builder()
                .name(request.getName())
                .email(request.getEmail())
                .speciality(request.getSpeciality())
                .degree(request.getDegree())
                .experience(request.getExperience())
                .about(request.getAbout())
                .fees(request.getFees())
                .address1(request.getAddress1())
                .address2(request.getAddress2())
                .build();
    }
}
