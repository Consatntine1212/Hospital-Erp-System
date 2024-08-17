package com.HospitalErp.serviceImplementation;

import com.HospitalErp.model.Prescription;
import com.HospitalErp.repository.PrescriptionRepository;
import com.HospitalErp.service.PrescriptionService;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class PrescriptionServiceImplementation implements PrescriptionService {
    private final PrescriptionRepository prescriptionRepository;

    public PrescriptionServiceImplementation(PrescriptionRepository prescriptionRepository) {
        this.prescriptionRepository = prescriptionRepository;
    }

    @Override
    public List<Prescription> getDoctorsPrescriptions(UUID doctor_id) {
        return prescriptionRepository.findByDoctorId(doctor_id);
    }

    @Override
    public Prescription createNewPPrescription(Prescription newPrescription) {
        return prescriptionRepository.save(newPrescription);
    }

    @Override
    public Prescription updatePrescription(Prescription newPrescription) {
        return prescriptionRepository.save(newPrescription);
    }

    @Override
    public Optional<Prescription> getPrescriptionById(Long prescription_id) {
        return prescriptionRepository.findById(prescription_id);
    }

    @Override
    public String FulfilPrescriptions(Long Prescription_id) {
        return null;
    }

    @Override
    public List<Prescription> getPatientPrescriptions(UUID patient_id) {
        return prescriptionRepository.findByPatientId(patient_id);
    }

    @Override
    public List<Prescription> getActivePrescriptions() {
        return prescriptionRepository.findAll();
    }
}
