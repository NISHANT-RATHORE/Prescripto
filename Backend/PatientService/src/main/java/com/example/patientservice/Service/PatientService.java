package com.example.patientservice.Service;

import com.example.patientservice.DTO.UpdateProfileDTO;
import com.example.patientservice.Model.Patient;
import com.example.patientservice.Repository.PatientRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
public class PatientService {
    private final PatientRepository patientRepository;
    private final CloudinaryService cloudinaryService;

    public PatientService(PatientRepository patientRepository, CloudinaryService cloudinaryService) {
        this.patientRepository = patientRepository;
        this.cloudinaryService = cloudinaryService;
    }

    public Patient getUser(String username) {
        Patient patient =  patientRepository.findByEmail(username);
        log.info("Patient found: {}", patient);
        return patient;
    }

    public Patient updatePatient(String username, UpdateProfileDTO dto) {
        Patient patient = patientRepository.findByEmail(username);
        if(patient == null){
            throw new RuntimeException("Patient not found");
        }
        if (dto.getPhone() != null) {
            patient.setPhone(dto.getPhone());
        }
        if (dto.getAddress1() != null) {
            patient.setAddress1(dto.getAddress1());
        }
        if (dto.getAddress2() != null) {
            patient.setAddress2(dto.getAddress2());
        }
        if(dto.getImage() != null){
            String imageUrl = cloudinaryService.uploadFile(dto.getImage(),"DoctorPhotos");
            patient.setImage(imageUrl);
        }
        if (dto.getGender() != null) {
            patient.setGender(dto.getGender());
        }
        if (dto.getDob() != null) {
            patient.setDob(dto.getDob());
        }
        return patientRepository.save(patient);
    }

    public Patient getPatient(String userId) {
        return patientRepository.findByUserId(userId);
    }


    public ResponseEntity<List<Patient>> getAllPatientData() {
        List<Patient> patients = patientRepository.findAll();
        if (patients.isEmpty()) {
            return ResponseEntity.noContent().build();
        } else {
            List<Patient> filteredPatients = patients.stream()
                    .filter(patient -> patient.getUserId() != null)
                    .collect(Collectors.toList());
            return ResponseEntity.ok(filteredPatients);
        }
    }
}
