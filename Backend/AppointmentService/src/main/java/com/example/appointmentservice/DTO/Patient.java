package com.example.appointmentservice.DTO;

import jakarta.validation.constraints.NotNull;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Data;
import lombok.experimental.FieldDefaults;

@Builder
@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Patient {
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
