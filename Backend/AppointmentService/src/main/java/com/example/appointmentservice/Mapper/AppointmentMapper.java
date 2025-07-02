package com.example.appointmentservice.Mapper;

import com.example.appointmentservice.DTO.AppointmentDTO;
import com.example.appointmentservice.Model.Appointment;
import lombok.experimental.UtilityClass;

import java.util.List;


@UtilityClass
public class AppointmentMapper {
    public Appointment mapToAppointment(AppointmentDTO request){
        return Appointment.builder()
                .doctorId(request.getDoctorData().getDoctorId())
                .slotTime(request.getSlotTime())
                .slotDate(request.getSlotDate())
                .build();
    }

    public static AppointmentDTO mapToAppointmentDTO(Appointment appointment) {
        return AppointmentDTO.builder()
                .appointmentId(appointment.getAppointmentId())
                .slotTime(appointment.getSlotTime())
                .slotDate(appointment.getSlotDate())
                .amount(appointment.getAmount())
                .date(appointment.getDate())
                .payment(appointment.isPayment())
                .cancelled(appointment.isCancelled())
                .build();
    }
}
