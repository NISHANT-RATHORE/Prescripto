package com.example.doctorservice.Service;

import com.example.doctorservice.DTO.AddDoctorRequest;
import com.example.doctorservice.DTO.DoctorDataRequest;
import com.example.doctorservice.DTO.ImageModel;
import com.example.doctorservice.Mapper.DoctorDataMapper;
import com.example.doctorservice.Mapper.DoctorMapper;
import com.example.doctorservice.Model.Doctor;
import com.example.doctorservice.Repository.DoctorRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.*;

@Service
@Slf4j
public class DoctorService {

    private final CloudinaryService cloudinaryService;
    private final DoctorRepository doctorRepository;

    @Autowired
    public DoctorService(CloudinaryService cloudinaryService, DoctorRepository doctorRepository) {
        this.cloudinaryService = cloudinaryService;
        this.doctorRepository = doctorRepository;
    }

    public Doctor addDoctor(@RequestPart(value = "docIm",required = false)MultipartFile docIm, @RequestPart("doctorData") AddDoctorRequest request) throws IOException {
        if (doctorRepository.findByEmail(request.getEmail()) != null) {
            return null; // Doctor already exists
        }
        log.info("image: " + docIm);
        log.info("request: " + request);
        ImageModel docImg = ImageModel.builder().file(docIm).build();
        String docImgURL = uploadImage(docImg);
        Doctor doctor = DoctorMapper.mapToDoctor(request);
        doctor.setRoles(new HashSet<>(List.of("Doctor","Admin")));
        doctor.setDocImg(docImgURL);
        doctor.setDate(new Date());
        doctor.setAvailable(true);
        return doctorRepository.save(doctor);
    }

    public List<Doctor> getAllDoctors() {
        return doctorRepository.findAll();
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

    public void changeAvailabilityByEmail(String email) {
        Optional<Doctor> doctorOptional = Optional.ofNullable(doctorRepository.findByEmail(email));
        if (doctorOptional.isPresent()) {
            Doctor doctor = doctorOptional.get();
            doctor.setAvailable(!doctor.getAvailable());
            doctorRepository.save(doctor);
        } else {
            throw new RuntimeException("Doctor not found with email: " + email);
        }
    }

    public DoctorDataRequest getDoctor(String doctorId) {
        Doctor Foundeddoctor =  doctorRepository.findByDoctorId(doctorId);
        return DoctorDataMapper.mapToDoctorDataRequest(Foundeddoctor);
    }

    public Doctor updateDoctor(DoctorDataRequest request) {
        Doctor doctor = doctorRepository.findByEmail(request.getEmail());
        if (doctor == null) {
            throw new RuntimeException("Doctor not found with email: " + request.getEmail());
        }
        return doctorRepository.save(doctor);
    }

    public List<DoctorDataRequest> getDoctorsData() {
        List<Doctor> doctors = doctorRepository.findAll();
        List<DoctorDataRequest> doctorDataRequests = new ArrayList<>();
        for(Doctor doctor : doctors){
            doctorDataRequests.add(DoctorDataMapper.mapToDoctorDataRequest(doctor));
        }
        return doctorDataRequests;
    }

    public void updateDoctorSlots(Doctor doctor) {
        Doctor existingDoctor = doctorRepository.findByEmail(doctor.getEmail());
        if (existingDoctor != null) {
            existingDoctor.setSlots(doctor.getSlots());
            doctorRepository.save(existingDoctor);
        } else {
            throw new RuntimeException("Doctor not found with email: " + doctor.getEmail());
        }
    }
}