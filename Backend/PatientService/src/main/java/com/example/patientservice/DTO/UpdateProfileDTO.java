package com.example.patientservice.DTO;

import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

@Data
public class UpdateProfileDTO {
    private String phone;
    private String address1;
    private String address2;
    private MultipartFile image; // for file upload
    private String gender;
    private String dob;
}
