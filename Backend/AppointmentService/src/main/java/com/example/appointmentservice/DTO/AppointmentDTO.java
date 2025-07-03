package com.example.appointmentservice.DTO;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.experimental.FieldDefaults;

import java.util.Date;

@Data
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class AppointmentDTO {
    Patient patientData;
    Doctor doctorData;
    String appointmentId;
    int amount;
    String slotTime;
    String slotDate;
    Date date;
    boolean cancelled;
    boolean payment;
}