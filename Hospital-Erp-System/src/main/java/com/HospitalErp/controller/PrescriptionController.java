package com.HospitalErp.controller;


import com.HospitalErp.DTO.SimplePrescriptionDTO;
import com.HospitalErp.model.Drug;
import com.HospitalErp.model.Prescription;
import com.HospitalErp.service.DrugService;
import com.HospitalErp.service.PrescriptionService;
import com.HospitalErp.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
public class PrescriptionController {

    private final PrescriptionService prescriptionService;
    private final DrugService drugService;
    private final UserService userService;


    public PrescriptionController(PrescriptionService prescriptionService, DrugService drugService, UserService userService) {
        this.prescriptionService = prescriptionService;
        this.drugService = drugService;
        this.userService = userService;
    }

    @GetMapping("/myDoctorPrescription")
    public List<Prescription> getDoctorPrescriptionDetails(@RequestParam UUID doctorId) {
        List<Prescription> prescription = prescriptionService.getDoctorsPrescriptions(doctorId);
        if (prescription != null ) {
            return prescription;
        }else {
            return null;
        }
    }

    @GetMapping("/myPatientPrescription")
    public List<Prescription> getPatientPrescriptionDetails(@RequestParam UUID patientId) {
        List<Prescription> prescription = prescriptionService.getPatientPrescriptions(patientId);
        if (prescription != null ) {
            return prescription;
        }else {
            return null;
        }
    }
//////////////////////////!!!!!!!!!!!!!!!!!!!!!
    @GetMapping("/SearchForPrescription")
    public List<Prescription> getPrescriptionDetails(@RequestParam UUID UserId) {
        List<Prescription> prescription = prescriptionService.getActivePrescriptions();
        if (prescription != null ) {
            return prescription;
        }else {
            return null;
        }
    }

    @PostMapping("/createPrescription")
    public ResponseEntity<Object> createPrescription(@RequestBody SimplePrescriptionDTO simplePrescription) {
        Prescription prescription = new Prescription();
        prescription.setPrescriptionsSummary(simplePrescription.getPrescriptionsSummary());
        prescription.setDoctor(userService.getUserById(simplePrescription.getDoctorId()).get());
        prescription.setPatient(userService.getUserById(simplePrescription.getPatientId()).get());
        Set<Drug> drugsSet = new HashSet<>();  // Use a HashSet instead of List
        List<Long> drugIds = simplePrescription.getDrugIds();
        for (Long number : drugIds) {
            Optional<Drug> drug = drugService.getDrugById(number);
            if (drug.isPresent()){
                drugsSet.add(drug.get());  // Add drugs to the HashSet
            }
        }
        prescription.setDrugs(drugsSet);  // Set the HashSet to the prescription
        try {
            // You might want to perform validation or additional checks before saving
            Prescription savedPrescription = prescriptionService.createNewPPrescription(prescription);
            if (savedPrescription != null) {
                return ResponseEntity.status(HttpStatus.CREATED)
                        .body(Map.of("message", "Prescription created successfully"));
            } else {
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                        .body(Map.of("message", "Failed to create prescription"));
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Map.of("message", "Error creating prescription: " + e.getMessage()));
        }
    }

    @GetMapping("/PrescriptionById")
    public Prescription getPatientPrescriptionById(@RequestParam Long prescriptionId) {
        Optional<Prescription> prescription = prescriptionService.getPrescriptionById(prescriptionId);
        return prescription.orElse(null);
    }
    @PostMapping("/updatePrescription")
    public ResponseEntity<Object> updatePrescription(@RequestBody SimplePrescriptionDTO simplePrescription) {
        // Log the incoming prescription using the logger
        System.out.println("Received Prescription: " + simplePrescription);

        // Fetch the existing prescription from the database
        Optional<Prescription> existingPrescriptionOpt = prescriptionService.getPrescriptionById(simplePrescription.getPrescriptionId());

        if (!existingPrescriptionOpt.isPresent()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(Map.of("message", "Prescription not found"));
        }

        Prescription existingPrescription = existingPrescriptionOpt.get();

        // Update fields if they are not null in the incoming request
        if (simplePrescription.getPrescriptionsSummary() != null) {
            existingPrescription.setPrescriptionsSummary(simplePrescription.getPrescriptionsSummary());
        }
        if (simplePrescription.getDoctorId() != null) { // Check for null instead of 0
            existingPrescription.setDoctor(userService.getUserById(simplePrescription.getDoctorId()).get()   );
        }
        if (simplePrescription.getPatientId() != null) { // Check for null instead of 0
            existingPrescription.setPatient(userService.getUserById(simplePrescription.getPatientId()).get());
        }
        if (simplePrescription.getStatus() != null) {
            existingPrescription.setStatus(simplePrescription.getStatus());
        }
        if (simplePrescription.getDrugIds() != null && !simplePrescription.getDrugIds().isEmpty()) {
            Set<Drug> drugsSet = new HashSet<>();
            for (Long drugId : simplePrescription.getDrugIds()) {
                Optional<Drug> drug = drugService.getDrugById(drugId);
                drug.ifPresent(drugsSet::add);
            }
            existingPrescription.setDrugs(drugsSet);
        }

        try {
            Prescription updatedPrescription = prescriptionService.updatePrescription(existingPrescription);
            if (updatedPrescription != null) {
                return ResponseEntity.status(HttpStatus.OK)
                        .body(Map.of("message", "Prescription updated successfully"));
            } else {
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                        .body(Map.of("message", "Failed to update prescription"));
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Map.of("message", "Error updating prescription: " + e.getMessage()));
        }
    }
}