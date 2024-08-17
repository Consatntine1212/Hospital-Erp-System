package com.HospitalErp.repository;

import com.HospitalErp.model.User;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface UserRepository extends CrudRepository<User, UUID> {
    Optional<User> findByEmail(String email);

    @Query(value = "SELECT * FROM user WHERE role = 'Doctor'", nativeQuery = true)
    List<User> findAllDoctors();

    @Query(value =
            "SELECT p.* " +
                    "FROM doctor_patient dp " +
                    "JOIN user d ON dp.doctor_id = d.user_id " +
                    "JOIN user p ON dp.patient_id = p.user_id " +
                    "WHERE d.role = 'Doctor' AND p.role = 'Patient' " + //spelled Patient
                    "AND d.user_id = :doctorId",
            nativeQuery = true)
    List<User> findPatientsByDoctorIdold(@Param("doctorId") String doctorId);


    @Query(value =
            "SELECT p.user_id, p.first_name, p.last_name, p.email, p.mobile_number,  p.role, p.gender, " +
                    "CASE WHEN dp.doctor_id IS NOT NULL THEN true ELSE false END AS isMyPatient " +
                    "FROM user p " +
                    "LEFT JOIN doctor_patient dp ON p.user_id = dp.patient_id AND dp.doctor_id = :doctorId " +
                    "WHERE p.role = 'Patient'",
            nativeQuery = true)
    List<Object[]> findPatientsByDoctorId(@Param("doctorId") String doctorId);


}