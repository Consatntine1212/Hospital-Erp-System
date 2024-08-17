package com.HospitalErp.serviceImplementation;

import com.HospitalErp.DTO.AppointmentDTO;
import com.HospitalErp.model.Prescription;
import com.HospitalErp.service.AppointmentService;
import com.HospitalErp.model.Appointment;
import com.HospitalErp.repository.AppointmentRepository;
import org.springframework.stereotype.Service;

import java.sql.Time;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class AppointmentServiceImplementation implements AppointmentService {
    private final AppointmentRepository appointmentRepository;

    public AppointmentServiceImplementation(AppointmentRepository appointmentRepository) {
        this.appointmentRepository = appointmentRepository;
    }

    @Override
    public Appointment updateAppointment(Appointment updatedAppointment) {
        return appointmentRepository.save(updatedAppointment);
    }

    @Override
    public void createAppointment(Appointment newAppointment) {
        this.appointmentRepository.save(newAppointment);
    }

    @Override
    public List<Appointment> getDoctorAppointments(UUID doctorId) {
        return appointmentRepository.findAppointmentsByDoctorId(doctorId);
    }

    @Override
    public List<Appointment> getPatientAppointments(UUID patientId) {
        return appointmentRepository.findByPatientId(patientId);
    }

    @Override
    public Optional<Appointment> getAppointmentsById(Long AppointmentId) {

        return appointmentRepository.findById(AppointmentId);
    }

    @Override
    public Boolean checkIfAppointmentExists(DayOfWeek dayOfWeek, LocalDate date, UUID doctorId, Long scheduleId) {
        return appointmentRepository.checkIfAppointmentExists( dayOfWeek,  date,  doctorId,  scheduleId);
    }

    @Override
    public List<AppointmentDTO> getAppointmentsByCriteria() {
        LocalDate currentDate = LocalDate.now();
        LocalDate dateTwoMonthsLater = currentDate.plusMonths(2);
        return appointmentRepository.findAppointmentsByCriteria(currentDate, dateTwoMonthsLater);
    }
}
