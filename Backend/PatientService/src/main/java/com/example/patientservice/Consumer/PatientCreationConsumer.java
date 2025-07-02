package com.example.patientservice.Consumer;

import com.example.patientservice.Model.Patient;
import com.example.patientservice.Repository.PatientRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

import static com.example.patientservice.Constants.KafkaConstants.PATIENT_CREATION_TOPIC;

@Service
@Slf4j
public class PatientCreationConsumer {
    private final ObjectMapper objectMapper;
    private final PatientRepository  patientRepository;

    public PatientCreationConsumer(ObjectMapper objectMapper, PatientRepository patientRepository) {
        this.objectMapper = objectMapper;
        this.patientRepository = patientRepository;
    }

    @KafkaListener(topics = PATIENT_CREATION_TOPIC,groupId = "patient-service")
    public void consumePatientCreation(String message) throws JsonProcessingException {
        try {
            System.out.println("Consumed patient: " + message);
            ObjectNode node = objectMapper.readValue(message, ObjectNode.class);
            Patient patient = objectMapper.treeToValue(node, Patient.class);
            patient.setDob(LocalDate.of(2000,1,1).toString());
            System.out.println("Patient: " + patient);
            patientRepository.save(patient);
            log.info("Patient saved successfully");
        } catch (Exception e) {
            log.error("Error while processing patient creation message: " + e.getMessage());
            throw e;
        }
    }
}
