package com.example.authservice.Configuration;

import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import static com.example.authservice.Constants.KafkaConstants.PATIENT_CREATION_TOPIC;

@Configuration
public class KafkaConfig {

    @Bean
    public NewTopic patientCreationTopic() {
        return new NewTopic(PATIENT_CREATION_TOPIC, 3, (short) 1);
    }
}
