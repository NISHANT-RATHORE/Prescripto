package com.example.authservice.DTO;

import com.example.authservice.Model.User;
import jakarta.validation.constraints.NotNull;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.FieldDefaults;

import java.util.Date;

@Builder
@EqualsAndHashCode(callSuper = true)
@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class PatientDTO extends User {
    String userId;
    @NotNull
    String name;
    String username;
    String password;
    String phone;
    String address1;
    String address2;
    String image;
    String gender;
}
