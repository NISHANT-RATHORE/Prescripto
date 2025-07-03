package com.example.appointmentservice.Service;

import com.example.appointmentservice.Client.DoctorServiceClient;
import com.example.appointmentservice.Client.PatientServiceClient;
import com.example.appointmentservice.DTO.AppointmentDTO;
import com.example.appointmentservice.DTO.Doctor;
import com.example.appointmentservice.DTO.Patient;
import com.example.appointmentservice.Mapper.AppointmentMapper;
import com.example.appointmentservice.Model.Appointment;
import com.example.appointmentservice.Repository.AppointmentRepository;
import com.example.appointmentservice.Utils.RazorPayUtil;
import com.razorpay.Order;
import com.razorpay.Payment;
import com.razorpay.RazorpayClient;
import com.razorpay.RazorpayException;
import lombok.extern.slf4j.Slf4j;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

@Service
@Slf4j
public class AppointmentService {
    @Value("${rzp_key_id}")
    private String keyId;

    @Value("${rzp_key_secret}")
    private String secret;

    private final AppointmentRepository appointmentRepository;
    private final DoctorServiceClient doctorServiceClient;
    private final PatientServiceClient userServiceClient;

    @Autowired
    public AppointmentService(AppointmentRepository appointmentRepository, DoctorServiceClient doctorServiceClient, PatientServiceClient userServiceClient, RazorPayUtil razorPayUtil) {
        this.appointmentRepository = appointmentRepository;
        this.doctorServiceClient = doctorServiceClient;
        this.userServiceClient = userServiceClient;
    }

    public boolean cancelAppointment(String appointmentId) {
        Appointment appointment = appointmentRepository.findByAppointmentId(appointmentId);
        if (appointment == null) {
            log.warn("No appointment found for id: {}", appointmentId);
            return false;
        }
        appointment.setCancelled(true);
        appointmentRepository.save(appointment);
        return true;
    }

    public String Payment(String appointmentId, RazorpayClient razorpayClient) throws RazorpayException {
        Appointment appointment = appointmentRepository.findByAppointmentId(appointmentId);
<<<<<<< HEAD
        if(appointment == null){
            log.warn("No appointment found for id: {}", appointmentId);
            return null;
        }
        int amount = appointment.getAmount()*100;
=======
        if (appointment == null) {
            log.warn("No appointment found for id: {}", appointmentId);
            return null;
        }
        int amount = appointment.getAmount() * 100;
>>>>>>> 16605277355c3ebd23f3adf839fa2bc5b8f5b201
        JSONObject orderRequest = new JSONObject();
        orderRequest.put("amount", amount);
        orderRequest.put("currency", "INR");
        orderRequest.put("receipt", "order_receipt_11");
        Order order = razorpayClient.orders.create(orderRequest);
        appointment.setPayment(true);
        appointmentRepository.save(appointment);
        return order.toString();
    }

    public boolean verifyPayment(String orderId) throws RazorpayException {
        RazorpayClient razorpayClient = new RazorpayClient(keyId, secret);
        List<Payment> payments = razorpayClient.orders.fetchPayments(orderId);
        return !payments.isEmpty();
    }

<<<<<<< HEAD
   public ResponseEntity<Doctor> getDoctor(String doctorId) {
       try {
           Doctor response = doctorServiceClient.getDoctor(doctorId);
           if (response == null) {
               log.warn("Doctor data not found for ID: {}", doctorId);
               return ResponseEntity.notFound().build();
           }
           return ResponseEntity.ok(response);
       } catch (Exception e) {
           log.error("Error fetching doctor data for ID: {}", doctorId, e);
           return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
       }
   }
=======
    public ResponseEntity<Doctor> getDoctor(String doctorId) {
        try {
            Doctor response = doctorServiceClient.getDoctor(doctorId);
            if (response == null) {
                log.warn("Doctor data not found for ID: {}", doctorId);
                return ResponseEntity.notFound().build();
            }
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            log.error("Error fetching doctor data for ID: {}", doctorId, e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
>>>>>>> 16605277355c3ebd23f3adf839fa2bc5b8f5b201

    public ResponseEntity<List<Doctor>> getDoctorData() {
        try {
            List<Doctor> response = doctorServiceClient.getDoctorsData();
            if (response == null || response.isEmpty()) {
                log.warn("No doctor data found");
                return ResponseEntity.status(HttpStatus.CONFLICT).body(new ArrayList<>());
            }
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            log.error("Error fetching doctor data", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ArrayList<>());
        }
    }

    public ResponseEntity<Patient> getPatient(String userId) {
        try {
            Patient response = userServiceClient.getPatient(userId);
            if (response == null) {
                log.warn("No patient data found for ID: {}", userId);
                return ResponseEntity.notFound().build();
            }
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            log.error("Error fetching patient data for ID: {}", userId, e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    public ResponseEntity<List<Patient>> getPatientsData() {
        try {
            List<Patient> response = userServiceClient.getPatientData();
            if (response == null || response.isEmpty()) {
                log.warn("No patient data found");
                return ResponseEntity.status(HttpStatus.CONFLICT).body(new ArrayList<>());
            }
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            log.error("Error fetching patient data", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ArrayList<>());
        }
    }

    public Appointment bookAppointment(AppointmentDTO appointmentDTO) {
        try {
            // Validate input
            if (appointmentDTO == null || appointmentDTO.getPatientData() == null || appointmentDTO.getDoctorData() == null) {
                throw new IllegalArgumentException("Invalid appointment data");
            }

            log.info("Booking Appointment initiated for slot: {} on {}", appointmentDTO.getSlotTime(), appointmentDTO.getSlotDate());
<<<<<<< HEAD
            log.info("PatientData :",appointmentDTO.getPatientData());
=======
            log.info("PatientData :", appointmentDTO.getPatientData());
>>>>>>> 16605277355c3ebd23f3adf839fa2bc5b8f5b201

            // Fetch doctor data
            Doctor docData = appointmentDTO.getDoctorData();
            if (docData == null) {
                throw new RuntimeException("Doctor data not found");
            }

            // Check and update slots
            String requestedSlot = appointmentDTO.getPatientData().getUserId() + " Date:" + appointmentDTO.getSlotDate() + " Time: " + appointmentDTO.getSlotTime();
            List<String> slots = docData.getSlots() != null ? docData.getSlots() : new ArrayList<>();
            synchronized (slots) {
                if (slots.contains(requestedSlot)) {
                    throw new IllegalArgumentException("Slot already booked");
                }
                slots.add(requestedSlot);
            }

            // Fetch patient data
            Patient user = appointmentDTO.getPatientData();
            if (user == null) {
                throw new RuntimeException("Patient data not found");
            }

            // Create and save appointment
            Appointment appointment = AppointmentMapper.mapToAppointment(appointmentDTO);
            appointment.setPatientId(appointmentDTO.getPatientData().getUserId());
            appointment.setAppointmentId(UUID.randomUUID().toString());
            appointment.setAmount(docData.getFees());
            appointment.setDate(new Date());
            appointmentRepository.save(appointment);

            // Update doctor slots asynchronously
            docData.setSlots(slots);
            doctorServiceClient.updateSlots(docData);

            log.info("Appointment booked successfully with ID: {}", appointment.getAppointmentId());
            return appointment;
        } catch (IllegalArgumentException e) {
            log.error("Invalid input while booking appointment: {}", e.getMessage());
            throw e;
        } catch (Exception e) {
            log.error("Error booking appointment", e);
            throw new RuntimeException("Failed to book appointment", e);
        }
    }


    public List<AppointmentDTO> getAppointmentsWithDetails(String patientId) {
        List<Appointment> appointments = appointmentRepository.findByPatientId(patientId);
        List<AppointmentDTO> appointmentDTOs = new ArrayList<>();

        for (Appointment appointment : appointments) {
            // Fetch doctor and patient data
            Doctor doctor = doctorServiceClient.getDoctor(appointment.getDoctorId());
            Patient patient = userServiceClient.getPatient(appointment.getPatientId());

            // Map Appointment to AppointmentDTO
            AppointmentDTO appointmentDTO = AppointmentMapper.mapToAppointmentDTO(appointment);
            appointmentDTO.setDoctorData(doctor);
            appointmentDTO.setPatientData(patient);

            appointmentDTOs.add(appointmentDTO);
        }

        return appointmentDTOs;
    }
<<<<<<< HEAD
=======

    public List<AppointmentDTO> getAllAppointments() {
        List<Appointment> appointments = appointmentRepository.findAll();
        List<AppointmentDTO> appointmentDTOs = new ArrayList<>();
        for (Appointment appointment : appointments) {
            // Fetch doctor and patient data
            Doctor doctor = doctorServiceClient.getDoctor(appointment.getDoctorId());
            Patient patient = userServiceClient.getPatient(appointment.getPatientId());

            // Map Appointment to AppointmentDTO
            AppointmentDTO appointmentDTO = AppointmentMapper.mapToAppointmentDTO(appointment);
            appointmentDTO.setDoctorData(doctor);
            appointmentDTO.setPatientData(patient);

            appointmentDTOs.add(appointmentDTO);
        }
        return appointmentDTOs;
    }

>>>>>>> 16605277355c3ebd23f3adf839fa2bc5b8f5b201
}
