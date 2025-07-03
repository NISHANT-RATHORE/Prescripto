package com.example.appointmentservice.DTO;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.experimental.FieldDefaults;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Data
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Doctor {
    String doctorId;
    String name;
    String email;
    String speciality;
    String degree;
    String experience;
    String about;
    Boolean available;
<<<<<<< HEAD
    int fees;
    String address1;
    String address2;
    Date date;
    List<String> slots = new ArrayList<>();
=======
    Integer fees;
    String address1;
    String address2;
    Date date;
    String authorities;
    List<String> slots;
>>>>>>> 16605277355c3ebd23f3adf839fa2bc5b8f5b201
    String docImg;
}