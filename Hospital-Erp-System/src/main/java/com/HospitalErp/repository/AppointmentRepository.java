package com.HospitalErp.repository;

import com.HospitalErp.DTO.AppointmentDTO;
import com.HospitalErp.model.Appointment;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.sql.Time;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Date;
import java.util.List;
import java.util.UUID;

@Repository
public interface AppointmentRepository extends CrudRepository<Appointment, Long> {

	List<Appointment> findByDoctorId(String doctor_id);
	@Query("SELECT a FROM Appointment a WHERE a.doctor.id = :doctorId AND a.patient is not null ")
	List<Appointment> findAppointmentsByDoctorId(@Param("doctorId") UUID doctorId);

	// Custom query to get all upcoming active Appointments by the doctorid
	List<Appointment> findAppointmentByDoctor_IdAndPatientIsNotNull(UUID doctorId);
	@Query("SELECT a FROM Appointment a  WHERE a.patient.id = :patient_id")
	List<Appointment> findByPatientId(@Param("patient_id") UUID patient_id);

	@Query("SELECT new com.HospitalErp.DTO.AppointmentDTO(" +
			"a.appointmentsId, " +
			"a.appointmentsDate, " +
			"ts.startTime, " +  // Assuming you have a method to get start time from TimeSlot
			"ds.dayOfWeek, " + // Using a function to get the day of the week
			"di.user.id, " +
			"di.user.firstName, " +
			"di.user.lastName, " +
			"a.patient.id, " +
			"a.appointmentsDescription, " +
			"a.createDt, " +
			"di.doctorSpecialization, " + // Adjust based on your Account structure
			"di.officeNo) " + // Assuming officeNo is part of DoctorSchedule
			"FROM Appointment a " +
			"JOIN DoctorSchedule ds ON a.schedule.id = ds.id " +
			"JOIN TimeSlot ts ON ds.timeSlot.id = ts.id " +
			"JOIN Account di ON ds.doctor.id = di.user.id " +
			"WHERE " +
			"(:appointmentsDateFrom IS NULL OR a.appointmentsDate BETWEEN :appointmentsDateFrom AND :appointmentsDateTo) AND " +
			"(a.patient.id IS NULL)"
	)
	List<AppointmentDTO> findAppointmentsByCriteria(
			@Param("appointmentsDateFrom") LocalDate appointmentsDateFrom,
			@Param("appointmentsDateTo") LocalDate appointmentsDateTo
	);
	@Query("SELECT a FROM Appointment a " +
			"JOIN DoctorSchedule ds ON a.schedule.id = ds.id " +
			"JOIN TimeSlot ts ON ds.timeSlot.id = ts.id " +
			"JOIN Account di ON ds.doctor.id = di.user.id " +
			"WHERE " +
			"(:appointmentsDateFrom IS NULL OR a.appointmentsDate BETWEEN :appointmentsDateFrom AND :appointmentsDateTo) AND " +
			"(a.patient.id IS NULL)"
	)
	List<Appointment> findAppointmentsByCriteriaOld(
			@Param("appointmentsDateFrom") LocalDate appointmentsDateFrom,
			@Param("appointmentsDateTo") LocalDate appointmentsDateTo
	);
	@Query("SELECT CASE WHEN COUNT(ap) > 0 THEN TRUE ELSE FALSE END " +
			"FROM Appointment ap " +
			"WHERE ap.doctor.id = :doctorId " +
			"AND ap.schedule.dayOfWeek = :dayOfWeek " +
			"AND ap.appointmentsDate = :date " +
			"AND ap.schedule.id = :scheduleId"
	)
	Boolean checkIfAppointmentExists(DayOfWeek dayOfWeek, LocalDate date, UUID doctorId, Long scheduleId);
}
