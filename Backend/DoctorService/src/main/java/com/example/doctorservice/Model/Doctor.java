package com.example.doctorservice.Model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.util.*;

@Entity
@Data
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "doctors")
@Builder
@JsonIgnoreProperties(ignoreUnknown = true)
public class Doctor{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;
    String doctorId;
    String name;
    String email;
    String speciality;
    String degree;
    String experience;
    String about;
    Boolean available;
    Integer fees;
    String address1;
    String address2;
    Date date;
    @ElementCollection
    List<String> slots = new ArrayList<>();

    @ElementCollection
    Set<String> roles = new HashSet<>();

    String docImg;

    @CreationTimestamp
    Date createdOn;

    @UpdateTimestamp
    Date updatedOn;
}