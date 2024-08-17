package com.HospitalErp.controller;


import com.HospitalErp.DTO.AppointmentDTO;
import com.HospitalErp.DTO.SimpleAppointmentDto;
import com.HospitalErp.DTO.SimplePrescriptionDTO;
import com.HospitalErp.model.Drug;
import com.HospitalErp.model.Prescription;
import com.HospitalErp.model.User;
import com.HospitalErp.service.AppointmentService;
import com.HospitalErp.model.Appointment;
import com.HospitalErp.service.UserService;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.*;

@RestController
public class AppointmentController {
    private final AppointmentService appointmentService;
    private final UserService userService;

    public AppointmentController(AppointmentService appointmentService, UserService userService) {
        this.appointmentService = appointmentService;
        this.userService = userService;
    }
    @GetMapping("/DoctorAppointment")
    public ResponseEntity<List<Appointment>> getDoctorAppointments(@RequestParam UUID doctorId) {
        List<Appointment> appointments = appointmentService.getDoctorAppointments(doctorId);
        return ResponseEntity.ok(appointments);
    }

    @GetMapping("/PatientAppointment")
    public ResponseEntity<List<Appointment>> getPatientAppointments(@RequestParam UUID patientId) {
        List<Appointment> appointments = appointmentService.getPatientAppointments(patientId);
        return ResponseEntity.ok(appointments);
    }

    @GetMapping("/AppointmentById")
    public ResponseEntity<Appointment> getAppointmentById(@RequestParam Long appointmentId) {
        Optional<Appointment> appointmentOptional = appointmentService.getAppointmentsById(appointmentId);
        return appointmentOptional.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping("/AppointmentByCriteria")
    public ResponseEntity<List<AppointmentDTO>> getAppointmentByCriteria() {
        List<AppointmentDTO> appointments = appointmentService.getAppointmentsByCriteria();
        return ResponseEntity.ok(appointments);
    }

    @PostMapping("/updateAppointment")
    public ResponseEntity<Object> updateAppointment(@RequestBody SimpleAppointmentDto simpleAppointment) {
        // Log the incoming prescription using the logger
        System.out.println("Received AppointmentDto: " + simpleAppointment);
        System.out.println("Updating Appointment: ");
        System.out.println("ID: " + simpleAppointment.getAppointmentsId());
        System.out.println("Date: " + simpleAppointment.getAppointmentsDate());
        System.out.println("Doctor ID: " + (simpleAppointment.getDoctorId()));
        System.out.println("Patient ID: " + (simpleAppointment.getPatientId()));
        System.out.println("Description: " + simpleAppointment.getAppointmentsDescription());
        // Fetch the existing Appointment from the database
        Optional<Appointment> existingAppointmentOpt = appointmentService.getAppointmentsById(simpleAppointment.getAppointmentsId());

        if (existingAppointmentOpt.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(Map.of("message", "Appointment not found"));
        }

        Appointment existingAppointment = existingAppointmentOpt.get();

        // Update fields if they are not null in the incoming request
        if (simpleAppointment.getAppointmentsDescription() != null) {
            existingAppointment.setAppointmentsDescription(simpleAppointment.getAppointmentsDescription());
        }
        if (simpleAppointment.getDoctorId() != null) { // Check for null instead of 0
            existingAppointment.setDoctor(userService.getUserById(simpleAppointment.getDoctorId()).get()   );
        }
        if (simpleAppointment.getPatientId() != null) { // Check for null instead of 0
            existingAppointment.setPatient(userService.getUserById(simpleAppointment.getPatientId()).get());
        }
        LocalDate now = LocalDate.now();
        existingAppointment.setCreateDt(now);
        System.out.println("Updating Appointment: ");
        System.out.println("ID: " + existingAppointment.getAppointmentsId());
        System.out.println("Date: " + existingAppointment.getAppointmentsDate());
        System.out.println("Schedule ID: " + (existingAppointment.getSchedule()));
        System.out.println("Doctor ID: " + (existingAppointment.getDoctor()));
        System.out.println("Patient ID: " + (existingAppointment.getPatient()));
        System.out.println("Description: " + existingAppointment.getAppointmentsDescription());
        System.out.println("Create Date: " + existingAppointment.getCreateDt());



        try {
            Appointment updatedAppointment = appointmentService.updateAppointment(existingAppointment);
            if (updatedAppointment != null) {
                return ResponseEntity.status(HttpStatus.OK)
                        .body(Map.of("message", "Appointment updated successfully"));
            } else {
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                        .body(Map.of("message", "Failed to update Appointment"));
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Map.of("message", "Error updating Appointment: " + e.getMessage()));
        }
    }

}



