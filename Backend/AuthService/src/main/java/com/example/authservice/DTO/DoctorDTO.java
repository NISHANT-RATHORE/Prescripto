package com.example.authservice.DTO;


import jakarta.persistence.ElementCollection;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.io.Serializable;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class DoctorDTO implements Serializable {
    String doctorId;
    String name;
    String email;
    String password;
    String speciality;
    String degree;
    String experience;
    String about;
    Boolean Available;
    Integer fees;
    String address1;
    String address2;
    Date date;
    String docImg;
}
