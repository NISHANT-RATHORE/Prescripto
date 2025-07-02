package com.example.appointmentservice.Client;


import com.example.appointmentservice.Configuration.FeignClientConfig;
import com.example.appointmentservice.DTO.Patient;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@FeignClient(name = "UserService",url = "${patient-service.url}",configuration = FeignClientConfig.class)
public interface PatientServiceClient {

    @GetMapping("/getPatient")
    Patient getPatient(@RequestParam String userId);

    @GetMapping("/getPatientData")
    List<Patient> getPatientData();
}
