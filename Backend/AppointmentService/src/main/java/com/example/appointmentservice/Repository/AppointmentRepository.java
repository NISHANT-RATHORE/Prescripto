package com.example.appointmentservice.Repository;

import com.example.appointmentservice.Model.Appointment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface AppointmentRepository extends JpaRepository<Appointment, String> {
//    List<Appointment> findByUserId(String username);
    Appointment findByAppointmentId(String appointmentId);
    List<Appointment> findByPatientId(String patientId);
}
