package com.HospitalErp.repository;

import com.HospitalErp.model.Prescription;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface PrescriptionRepository extends CrudRepository<Prescription, Long> {

    List<Prescription> findByDoctorId(UUID doctorId);
    List<Prescription> findByPatientId(UUID patientId);
    List<Prescription> findAll();
}
