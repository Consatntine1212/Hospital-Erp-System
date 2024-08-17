package com.HospitalErp.service;

import com.HospitalErp.DTO.AppointmentDTO;
import com.HospitalErp.model.Appointment;

import java.sql.Time;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface AppointmentService {

    Appointment updateAppointment(Appointment updatedAppointment);
    void createAppointment(Appointment newAppointment);
    List<Appointment> getDoctorAppointments(UUID doctorId);
    List<Appointment> getPatientAppointments(UUID patientId);
    Optional<Appointment> getAppointmentsById(Long AppointmentId);
    Boolean checkIfAppointmentExists(DayOfWeek dayOfWeek, LocalDate date, UUID doctorId, Long scheduleId);
    List<AppointmentDTO> getAppointmentsByCriteria();
}
