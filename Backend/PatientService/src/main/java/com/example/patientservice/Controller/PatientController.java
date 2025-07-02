package com.example.patientservice.Controller;

import com.example.patientservice.DTO.UpdateProfileDTO;
import com.example.patientservice.Model.Patient;
import com.example.patientservice.Service.JwtService;
import com.example.patientservice.Service.PatientService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/patient")
@Slf4j
//@CrossOrigin(origins = "${FRONTEND_URL}")
public class PatientController {

    private final PatientService userService;
    private final JwtService jwtService;
    private final PatientService patientService;

    public PatientController(PatientService userService, JwtService jwtService, PatientService patientService) {
        this.userService = userService;
        this.jwtService = jwtService;
        this.patientService = patientService;
    }

    @GetMapping("/getProfile")
    public ResponseEntity<Patient> getProfile(@RequestParam String userId) {
        try {
//            String username = SecurityContextHolder.getContext().getAuthentication().getName();
            Patient user =  patientService.getPatient(userId);
            if (user == null) {
                log.warn("No user found");
                return ResponseEntity.status(HttpStatus.CONFLICT).body(null);
            }
            log.info("Retrieved user profile successfully");
            return ResponseEntity.ok(user);
        } catch (Exception e) {
            log.error("Error retrieving user profile", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    @PutMapping(value = "/updateProfile", consumes = "multipart/form-data")
    public ResponseEntity<Patient> updateProfile(
            @ModelAttribute UpdateProfileDTO updateProfileDTO,
            @RequestParam String username
            ) {
        try {
//            String username = SecurityContextHolder.getContext().getAuthentication().getName();
            Patient updatedPatient = patientService.updatePatient(username, updateProfileDTO);
            log.info("Patient updated successfully");
            return ResponseEntity.ok(updatedPatient);
        } catch (Exception e) {
            log.error("Error updating patient profile", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    @GetMapping("/getPatient")
    public ResponseEntity<Patient> getPatient(@RequestParam String userId) {
        try {
            Patient patient = patientService.getPatient(userId);
            if (patient == null) {
                log.warn("No patient found with patientId: " + userId);
                return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
            }
            log.info("Retrieved Patient successfully");
            return ResponseEntity.ok(patient);
        } catch (Exception e) {
            log.error("Error retrieving Patient", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @GetMapping("/getPatientData")
    ResponseEntity<List<Patient>> getPatientData(){
        return patientService.getAllPatientData();
    }
}
