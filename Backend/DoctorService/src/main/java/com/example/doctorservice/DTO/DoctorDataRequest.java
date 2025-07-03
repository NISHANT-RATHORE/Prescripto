package com.example.doctorservice.DTO;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.experimental.FieldDefaults;

import java.util.Date;
import java.util.List;

@Data
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class DoctorDataRequest {
<<<<<<< HEAD
=======
    String doctorId;
>>>>>>> 16605277355c3ebd23f3adf839fa2bc5b8f5b201
    String name;
    String email;
    String speciality;
    String degree;
    String experience;
    String about;
<<<<<<< HEAD
    Boolean Available;
=======
    Boolean available;
>>>>>>> 16605277355c3ebd23f3adf839fa2bc5b8f5b201
    Integer fees;
    String address1;
    String address2;
    Date date;
    String authorities;
    List<String> slots;
    String docImg;
}
