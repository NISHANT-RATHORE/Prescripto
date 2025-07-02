package com.example.authservice.Service;

import com.example.authservice.DTO.DoctorDTO;
import com.example.authservice.DTO.ImageModel;
import com.example.authservice.DTO.PatientDTO;
import com.example.authservice.Mapper.DoctorMapper;
import com.example.authservice.Mapper.PatientMapper;
import com.example.authservice.Mapper.UserMapper;
import com.example.authservice.Model.User;
import com.example.authservice.Model.UserRole;
import com.example.authservice.Repository.UserRepository;
import com.example.authservice.Repository.UserRoleRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaOperations;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.transaction.KafkaTransactionManager;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.Collections;
import java.util.Optional;
import java.util.UUID;

import static com.example.authservice.Constants.DoctorCreationTopicConstants.*;
import static com.example.authservice.Constants.KafkaConstants.DOCTOR_CREATION_TOPIC;
import static com.example.authservice.Constants.KafkaConstants.PATIENT_CREATION_TOPIC;
import static com.example.authservice.Constants.UserCreationTopicConstants.*;
import static com.example.authservice.Constants.UserCreationTopicConstants.EMAIL;

@Service
@Slf4j
public class UserService implements UserDetailsService {
    private final UserRepository userRepository;
    private final CloudinaryService cloudinaryService;
    private final PasswordEncoder passwordEncoder;
    private final UserRoleRepository userRoleRepository;
    private final ObjectMapper objectMapper;
    private final KafkaTemplate<String, String> kafkaTemplate;
    private final KafkaTransactionManager kafkaTransactionManager;
    private final KafkaOperations kafkaOperations;

    @Autowired
    public UserService(UserRepository userRepository, CloudinaryService cloudinaryService, PasswordEncoder passwordEncoder, UserRoleRepository userRoleRepository, ObjectMapper objectMapper, KafkaTemplate<String, String> kafkaTemplate, KafkaTransactionManager kafkaTransactionManager, KafkaOperations kafkaOperations) {
        this.userRepository = userRepository;
        this.cloudinaryService = cloudinaryService;
        this.passwordEncoder = passwordEncoder;
        this.userRoleRepository = userRoleRepository;
        this.objectMapper = objectMapper;
        this.kafkaTemplate = kafkaTemplate;
        this.kafkaTransactionManager = kafkaTransactionManager;
        this.kafkaOperations = kafkaOperations;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByUsername(username);
        if (user == null) {
            throw new UsernameNotFoundException("User not found");
        }
        return user;
    }

    @Transactional(transactionManager = "kafkaTransactionManager")
    public Boolean signupUser(User user) {
        if (userRepository.findByUsername(user.getUsername()) != null) {
            return false;
        }
        User patient = UserMapper.toUser(user);
        patient.setUserId(UUID.randomUUID().toString());
        patient.setPassword(passwordEncoder.encode(patient.getPassword()));
        UserRole patientRole = new UserRole("Patient");
        patient.setRoles(Collections.singleton(patientRole));
        userRoleRepository.save(patientRole);
        userRepository.save(patient);
        log.info("Patient created: {}", patient);

        //publish patient to kafka
        kafkaTemplate.executeInTransaction(kafkaOperations -> {
            PatientDTO patientDTO = PatientMapper.toPatientDTO();
            patientDTO.setUserId(patient.getUserId());
            patientDTO.setName(patient.getName());

            ObjectNode objectNode = objectMapper.createObjectNode();
            objectNode.put(USERID, patientDTO.getUserId());
            objectNode.put(NAME, patientDTO.getName());
            objectNode.put(PHONE, patientDTO.getPhone());
            objectNode.put(EMAIL, patient.getUsername());
            objectNode.put(ADDRESS1, patientDTO.getAddress1());
            objectNode.put(ADDRESS2, patientDTO.getAddress2());
            objectNode.put(IMAGE, patientDTO.getImage());
            objectNode.put(GENDER, patientDTO.getGender());

            String kafkaMessage = objectNode.toString();
            kafkaOperations.send(PATIENT_CREATION_TOPIC, kafkaMessage);
            log.info("Message published to Kafka: {}", kafkaMessage);
            return true;
        });
        return true;
    }

    public Boolean adminSignupUser(User user) {
        if (userRepository.findByUsername(user.getUsername()) != null) {
            return false;
        }
        User admin = UserMapper.toUser(user);
        admin.setUserId(UUID.randomUUID().toString());
        admin.setPassword(passwordEncoder.encode(admin.getPassword()));
        UserRole adminRole = new UserRole("Admin");
        admin.setRoles(Collections.singleton(adminRole));
        userRoleRepository.save(adminRole);
        userRepository.save(admin);
        log.info("Admin created: {}", admin);
        return true;
    }

    public String uploadImage(ImageModel imageModel) {
        try {
            if (imageModel.getFile().isEmpty()) {
                return null;
            }
            String imageUrl = cloudinaryService.uploadFile(imageModel.getFile(), "DoctorPhotos");
            if (imageUrl == null) {
                return null;
            }
            return imageUrl;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @Transactional(transactionManager = "kafkaTransactionManager")
    public Boolean doctorSignupUser(DoctorDTO user, MultipartFile file) {
        if (userRepository.findByUsername(user.getEmail()) != null) {
            return false;
        }
        User doctor = UserMapper.toUser(new User(user.getName(),user.getEmail(),user.getPassword()));
        doctor.setUserId(UUID.randomUUID().toString());
        doctor.setPassword(passwordEncoder.encode(doctor.getPassword()));
        UserRole doctorRole = new UserRole("Doctor");
        doctor.setRoles(Collections.singleton(doctorRole));
        ImageModel docImg = ImageModel.builder().file(file).build();
        String docImgURL = uploadImage(docImg);
        if(docImgURL == null){
            log.error("image is not uploaded sucessfully");
            return false;
        }
        userRoleRepository.save(doctorRole);
        userRepository.save(doctor);
        log.info("Doctor created: {}", doctor);

        //publish doctor to kafka
        kafkaTemplate.executeInTransaction(kafkaOperations -> {
            DoctorDTO doctorDTO = DoctorMapper.mapToDoctor(user);
            doctorDTO.setDoctorId(doctor.getUserId());
            doctorDTO.setAvailable(true);
            doctorDTO.setDocImg(docImgURL);

            ObjectNode objectNode = objectMapper.createObjectNode();
            objectNode.put(DOCTORID, doctorDTO.getDoctorId());
            objectNode.put(DNAME, doctorDTO.getName());
            objectNode.put(SPECIALITY, doctorDTO.getSpeciality());
            objectNode.put(DEGREE, doctorDTO.getDegree());
            objectNode.put(EXPERIENCE, doctorDTO.getExperience());
            objectNode.put(ABOUT, doctorDTO.getAbout());
            objectNode.put(AVAILABLE, doctorDTO.getAvailable());
            objectNode.put(FEES, doctorDTO.getFees());
            objectNode.put(DADDRESS1, doctorDTO.getAddress1());
            objectNode.put(DADDRESS2, doctorDTO.getAddress2());
            objectNode.put(DOCIMG, doctorDTO.getDocImg());
            objectNode.put(EMAIL,doctorDTO.getEmail());

            String kafkaMessage = objectNode.toString();
            kafkaOperations.send(DOCTOR_CREATION_TOPIC, kafkaMessage);
            log.info("Message published to Kafka: {}", kafkaMessage);
            return true;
        });

        return true;
    }

    public String getUserByUsername(String name) {
        return Optional.of(userRepository.findByUsername(name)).map(User::getUserId).orElse(null);
    }
}