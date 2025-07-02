package com.example.doctorservice.DTO;

import lombok.Builder;
import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

@Data
@Builder
public class ImageModel {
//    private String name;
    private MultipartFile file;
}