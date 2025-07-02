package com.example.doctorservice.Consumer;

import com.example.doctorservice.Model.Doctor;
import com.example.doctorservice.Repository.DoctorRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import java.util.Date;

import static com.example.doctorservice.Constants.KafkaConstants.DOCTOR_CREATION_TOPIC;

@Service
@Slf4j
public class DoctorCreationConsumer {

    private final ObjectMapper objectMapper;
    private final DoctorRepository doctorRepository;

    public DoctorCreationConsumer(ObjectMapper objectMapper, DoctorRepository doctorRepository) {
        this.objectMapper = objectMapper;
        this.doctorRepository = doctorRepository;
    }

    @KafkaListener(topics = DOCTOR_CREATION_TOPIC, groupId = "doctor-service")
    public void consumeDoctorCreation(String message) throws JsonProcessingException {
        try {
            System.out.println("Consumed doctor: " + message);
            ObjectNode node = objectMapper.readValue(message, ObjectNode.class);
            Doctor doctor = objectMapper.treeToValue(node, Doctor.class);
            doctor.setDate(new Date());
            doctor.setAvailable(true);
            System.out.println("Doctor: " + doctor);

            doctorRepository.save(doctor);
            log.info("Doctor saved successfully");
        } catch (Exception e) {
            log.error("Error while processing doctor creation message: " + e.getMessage());
            throw e;
        }
    }

}
