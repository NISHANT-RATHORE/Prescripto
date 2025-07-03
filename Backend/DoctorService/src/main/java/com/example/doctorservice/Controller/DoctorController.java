package com.example.doctorservice.Controller;

import com.example.doctorservice.DTO.AddDoctorRequest;
import com.example.doctorservice.DTO.DoctorDataRequest;
import com.example.doctorservice.Model.Doctor;
import com.example.doctorservice.Service.DoctorService;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/doctor")
//@CrossOrigin(origins = "${FRONTEND_URL}")
public class DoctorController {
    private static final Logger log = LoggerFactory.getLogger(DoctorController.class);
    private final DoctorService doctorService;

    @Autowired
    public DoctorController(DoctorService doctorService) {
        this.doctorService = doctorService;
    }

    @GetMapping("/list")
    public ResponseEntity<List<Doctor>> allDoctors() {
        try {
            List<Doctor> allDoctors = doctorService.getAllDoctors();
            List<Doctor> filteredDoctors = allDoctors.stream()
                    .filter(doctor -> !doctor.getEmail().equals("appointmentService@gmail.com"))
                    .collect(Collectors.toList());
            if (filteredDoctors.isEmpty()) {
                log.warn("No doctors found");
                return ResponseEntity.status(HttpStatus.CONFLICT).body(Collections.emptyList());
            }
            log.info("Retrieved all doctors successfully");
            return ResponseEntity.ok(filteredDoctors);
        } catch (Exception e) {
            log.error("Error retrieving doctors", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(Collections.emptyList());
        }
    }

    @GetMapping("/getDoctor")
    public ResponseEntity<DoctorDataRequest> getDoctor(@RequestParam String doctorId) {
        try {
            DoctorDataRequest doctor = doctorService.getDoctor(doctorId);
            if (doctor == null) {
                log.warn("No doctor found with doctorId: " + doctorId);
                return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
            }
            log.info("Retrieved doctor successfully");
            return ResponseEntity.ok(doctor);
        } catch (Exception e) {
            log.error("Error retrieving doctor", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @GetMapping("/getDoctorData")
    public ResponseEntity<List<DoctorDataRequest>> getDoctorsData() {
        try {
            List<DoctorDataRequest> doctorDataRequests = doctorService.getDoctorsData();
            if (doctorDataRequests == null || doctorDataRequests.isEmpty()) {
                log.warn("No data found for doctors");
                return ResponseEntity.status(HttpStatus.CONFLICT).body(Collections.emptyList());
            }
            log.info("Retrieved all doctors data successfully");
            return ResponseEntity.ok(doctorDataRequests);
        } catch (Exception e) {
            log.error("Error retrieving doctors", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(Collections.emptyList());
        }
    }

    @PutMapping("/slotBooked")
    public ResponseEntity<String> bookingSlots(@RequestBody DoctorDataRequest request) {
        try {
            Doctor doctor = doctorService.updateDoctor(request);
            if (doctor == null) {
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("error in updating the patient");
            }
            return ResponseEntity.status(HttpStatus.OK).body("Successfully Updated the profile");
        } catch (Exception e) {
            log.warn(String.valueOf(e));
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error in updating the doctor");
        }
    }

    //Admin
    @PostMapping(value = "/addDoctor", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<?> addDoctor(
            @RequestPart("docImg") MultipartFile docImg,
            @RequestPart("doctorData") @Valid AddDoctorRequest request) throws IOException {

        log.info("Registering Doctor with {}", request.getEmail());

        Doctor doctor = doctorService.addDoctor(docImg, request);
        if (doctor == null) {
            return ResponseEntity.status(HttpStatus.CONFLICT)
                    .body(Map.of("message", "Doctor already exists"));
        }

        log.info("Registered successfully....");
        return ResponseEntity.ok(doctor);
    }


<<<<<<< HEAD
    @PostMapping("/changeAvailability/{email}")
    public ResponseEntity<?> changeAvailable(@PathVariable String email) {
        try {
            doctorService.changeAvailabilityByEmail(email);
=======
    @PutMapping("/changeAvailability/{doctorId}")
    public ResponseEntity<?> changeAvailable(@PathVariable String doctorId) {
        try {
            doctorService.changeAvailabilityByEmail(doctorId);
>>>>>>> 16605277355c3ebd23f3adf839fa2bc5b8f5b201
            log.info("Successfully changed the availability");
            return ResponseEntity.status(HttpStatus.OK).body("Successfully changed the availability");
        } catch (Exception e) {
            log.error("Error in changing availability", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error in changing availability");
        }
    }

    @PutMapping("/update-slots")
    public ResponseEntity<Void> updateSlots(@RequestBody Doctor doctor) {
        try {
            doctorService.updateDoctorSlots(doctor);
            log.info("Successfully updated slots for doctor with ID: {}", doctor.getDoctorId());
            return ResponseEntity.ok().build();
        } catch (Exception e) {
            log.error("Error updating slots for doctor with ID: {}", doctor.getDoctorId(), e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

}
