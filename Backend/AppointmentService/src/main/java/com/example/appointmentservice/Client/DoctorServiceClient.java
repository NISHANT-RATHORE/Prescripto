package com.example.appointmentservice.Client;

import com.example.appointmentservice.Configuration.FeignClientConfig;
import com.example.appointmentservice.DTO.Doctor;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@FeignClient(name = "DoctorService", url = "${doctor-service.url}",configuration = FeignClientConfig.class)
public interface DoctorServiceClient {
    @GetMapping("/getDoctor")
    Doctor getDoctor(@RequestParam String doctorId);

    @GetMapping("/getDoctorData")
    List<Doctor> getDoctorsData();

    @PutMapping("/update-slots")
    Void updateSlots(@RequestBody Doctor doctor);
}