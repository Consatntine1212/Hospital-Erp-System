package com.HospitalErp.repository;

import com.HospitalErp.model.User;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Repository
public interface CustomerRepository extends CrudRepository<User, Long> {

    List<User> findByEmail(String email);

    @Query(value =
            "SELECT p.* " +
                    "FROM doctor_patient dp " +
                    "JOIN user d ON dp.doctor_id = d.user_id " +
                    "JOIN user p ON dp.patient_id = p.user_id " +
                    "WHERE d.role = 'Doctor' AND p.role = 'Patient' " + //spelled Patient
                    "AND d.user_id = :doctorId",
            nativeQuery = true)
    ArrayList<User> findPatientsByDoctorId(@Param("doctorId") UUID doctorId);
}
