package com.HospitalErp.service;

import com.HospitalErp.model.Prescription;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface PrescriptionService {

    List<Prescription> getDoctorsPrescriptions(UUID doctor_id);
    Prescription createNewPPrescription(Prescription newPrescription);
    Prescription updatePrescription(Prescription newPrescription);

    Optional<Prescription> getPrescriptionById(Long prescription_id);
    String FulfilPrescriptions(Long Prescription_id); // pending
    List<Prescription> getPatientPrescriptions(UUID patient_id);

    List<Prescription> getActivePrescriptions();

}
