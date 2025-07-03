package com.example.doctorservice.Repository;

import com.example.doctorservice.Model.Doctor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DoctorRepository extends JpaRepository<Doctor, Long> {
    Doctor findByEmail(String username);

    Doctor findByDoctorId(String doctorId);
}

