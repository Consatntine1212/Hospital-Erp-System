package com.HospitalErp.repository;

import com.HospitalErp.model.DoctorSchedule;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface DoctorScheduleRepository extends CrudRepository<DoctorSchedule, Long> {
    @Query("SELECT ds FROM DoctorSchedule ds " +
            "WHERE ds.doctor.id = :doctor_id " +
            "ORDER BY ds.dayOfWeek, ds.timeSlot.startTime")
    List<DoctorSchedule> findByDoctorId(UUID doctor_id);
    @Query("SELECT ds FROM DoctorSchedule ds " +
            "WHERE ds.doctor.id = :doctor_id " +
            "AND ds.isAvailable = true " )
    List<DoctorSchedule> findAllActiveSchedule(UUID doctor_id);

    Optional<DoctorSchedule> findById(Long id);

}
