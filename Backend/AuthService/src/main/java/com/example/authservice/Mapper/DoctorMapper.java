package com.example.authservice.Mapper;

import com.example.authservice.DTO.DoctorDTO;
import lombok.experimental.UtilityClass;

@UtilityClass
public class DoctorMapper {
    public static DoctorDTO mapToDoctor(DoctorDTO doctorDTO){
        return DoctorDTO.builder()
                .name(doctorDTO.getName())
                .email(doctorDTO.getEmail())
                .speciality(doctorDTO.getSpeciality())
                .degree(doctorDTO.getDegree())
                .experience(doctorDTO.getExperience())
                .about(doctorDTO.getAbout())
                .fees(doctorDTO.getFees())
                .address1(doctorDTO.getAddress1())
                .address2(doctorDTO.getAddress2())
                .docImg(doctorDTO.getDocImg())
                .build();
    }
}
