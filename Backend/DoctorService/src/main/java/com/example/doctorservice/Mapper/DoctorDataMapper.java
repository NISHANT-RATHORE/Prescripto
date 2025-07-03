package com.example.doctorservice.Mapper;

import com.example.doctorservice.DTO.DoctorDataRequest;
import com.example.doctorservice.Model.Doctor;
import lombok.experimental.UtilityClass;

@UtilityClass
public class DoctorDataMapper {
    public static DoctorDataRequest mapToDoctorDataRequest(Doctor doctor) {
        return DoctorDataRequest.builder()
<<<<<<< HEAD
=======
                .doctorId(doctor.getDoctorId())
>>>>>>> 16605277355c3ebd23f3adf839fa2bc5b8f5b201
                .name(doctor.getName())
                .email(doctor.getEmail())
                .speciality(doctor.getSpeciality())
                .degree(doctor.getDegree())
                .experience(doctor.getExperience())
                .about(doctor.getAbout())
                .fees(doctor.getFees())
<<<<<<< HEAD
=======
                .available(doctor.getAvailable())
>>>>>>> 16605277355c3ebd23f3adf839fa2bc5b8f5b201
                .address1(doctor.getAddress1())
                .address2(doctor.getAddress2())
                .docImg(doctor.getDocImg())
                .build();
    }
}
