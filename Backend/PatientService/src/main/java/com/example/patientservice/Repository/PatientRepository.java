package com.example.patientservice.Repository;

import com.example.patientservice.Model.Patient;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PatientRepository extends JpaRepository<Patient,String> {

    Patient findByEmail(String username);

    Patient findByUserId(String userId);
}
