package com.example.appointmentservice.Controller;

import com.example.appointmentservice.Client.DoctorServiceClient;
import com.example.appointmentservice.Client.PatientServiceClient;
import com.example.appointmentservice.DTO.AppointmentDTO;
import com.example.appointmentservice.DTO.Doctor;
import com.example.appointmentservice.DTO.Patient;
import com.example.appointmentservice.Model.Appointment;
import com.example.appointmentservice.Service.AppointmentService;
import com.razorpay.RazorpayClient;
import com.razorpay.RazorpayException;
import lombok.extern.slf4j.Slf4j;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/appointment")
public class AppointmentController {

    @Value("${rzp_key_id}")
    private String keyId;

    @Value("${rzp_key_secret}")
    private String secret;

    private final DoctorServiceClient doctorServiceClient;
    private final PatientServiceClient userServiceClient;
    private final AppointmentService appointmentService;

    @Autowired
    public AppointmentController(DoctorServiceClient doctorServiceClient, PatientServiceClient userServiceClient, AppointmentService appointmentService) {
        this.doctorServiceClient = doctorServiceClient;
        this.userServiceClient = userServiceClient;
        this.appointmentService = appointmentService;
    }

    @GetMapping("/getDoctor")
    public ResponseEntity<Doctor> getDoctor(@RequestParam String doctorId){
        try{
            return appointmentService.getDoctor(doctorId);
        } catch (Exception e){
            log.error("Error retrieving doctor", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @GetMapping("/getAllDoctorData")
    public ResponseEntity<List<Doctor>> getDoctorsData(){
        try{
            return appointmentService.getDoctorData();
        } catch (Exception e){
            log.error("Error retrieving doctor", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }


    @GetMapping("/getUser")
    public ResponseEntity<Patient> getUser(@RequestParam String userId){
        try{
            return appointmentService.getPatient(userId);
        } catch (Exception e){
            log.error("Error retrieving doctor", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @GetMapping("/getAllUserData")
    public ResponseEntity<List<Patient>> getUserData(){
        try{
            return appointmentService.getPatientsData();
        } catch (Exception e){
            log.error("Error retrieving doctor", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @PostMapping("/bookAppointment")
    public ResponseEntity<Appointment> bookAppointment(@RequestBody AppointmentDTO appointmentDTO) {
        try {
            log.info("Booking Appointment initiated....");
            Appointment appointment = appointmentService.bookAppointment(appointmentDTO);
            log.info("Appointment booked successfully");
            return ResponseEntity.ok(appointment);
        } catch (Exception e) {
            log.error("Error booking appointment", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @GetMapping("/getAppointments")
    public ResponseEntity<List<AppointmentDTO>> getAppointments(@RequestParam String patientId) {
        try {
            log.info("Retrieving appointments for user: {}", patientId);
            List<AppointmentDTO> appointments = appointmentService.getAppointmentsWithDetails(patientId);
            log.info("Appointments retrieved successfully");
            return ResponseEntity.ok(appointments);
        } catch (Exception e) {
            log.error("Error retrieving appointments", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @PutMapping("/cancelAppointment")
    public ResponseEntity<String> cancelAppointment(@RequestParam String appointmentId) {
        try {
            log.info("Cancelling appointment: {}", appointmentId);
            boolean isCancelled = appointmentService.cancelAppointment(appointmentId);
            if (!isCancelled) {
                log.warn("Appointment not found");
                return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
            }
            log.info("Appointment cancelled successfully");
            return ResponseEntity.ok("Appointment cancelled successfully");
        } catch (Exception e) {
            log.error("Error cancelling appointment", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

<<<<<<< HEAD
=======
    @GetMapping("/allAppointments")
    public ResponseEntity<List<AppointmentDTO>> getAllAppointments() {
        try {
            log.info("Retrieving all appointments");
            List<AppointmentDTO> appointments = appointmentService.getAllAppointments();
            if (appointments.isEmpty()) {
                log.warn("No appointments found");
                return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
            }
            log.info("All appointments retrieved successfully");
            return ResponseEntity.ok(appointments);
        } catch (Exception e) {
            log.error("Error retrieving all appointments", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

>>>>>>> 16605277355c3ebd23f3adf839fa2bc5b8f5b201
    @GetMapping("/payment")
    public ResponseEntity<String> Payment(@RequestParam String appointmentId) throws RazorpayException {
        try {
            log.info("Payment initiated....");
            RazorpayClient razorpayClient = new RazorpayClient(keyId, secret);
            String order = appointmentService.Payment(appointmentId, razorpayClient);
            log.info("Payment successful");
            return ResponseEntity.ok(order);
        } catch (Exception e) {
            log.error("Error processing payment", e);
            JSONObject errorResponse = new JSONObject();
            errorResponse.put("error", "Error processing payment");
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error processing payment");
        }
    }

    @PostMapping("/verifyPayment")
    public ResponseEntity<String> verifyPayment(@RequestParam(name = "razorpay_order_id") String orderId) {
        try {
            boolean isValid = appointmentService.verifyPayment(orderId);
            if (isValid) {
                return ResponseEntity.ok("Payment verified successfully");
            } else {
                return ResponseEntity.status(400).body("Payment verification failed");
            }
        } catch (Exception e) {
            return ResponseEntity.status(500).body("Error verifying payment");
        }
    }
}
