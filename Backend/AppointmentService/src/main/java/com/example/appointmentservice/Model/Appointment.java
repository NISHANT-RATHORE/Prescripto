package com.example.appointmentservice.Model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
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
@Entity(name = "appointment")
public class Appointment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;
    String appointmentId;
    String patientId; // Store only the patient ID
    String doctorId;
    String slotDate;
    String slotTime;
    int amount;
    Date date;
    boolean cancelled;
    boolean payment;
    boolean isCompleted;

    public Appointment() {

    }
}