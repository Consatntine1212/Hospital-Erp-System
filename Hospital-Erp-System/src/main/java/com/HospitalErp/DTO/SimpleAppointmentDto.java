package com.HospitalErp.DTO;

import java.time.LocalDate;
import java.util.UUID;

public class SimpleAppointmentDto {
    private Long appointmentsId;
    private LocalDate appointmentsDate;
    private UUID doctorId;
    private UUID patientId;
    private String appointmentsDescription;

    public SimpleAppointmentDto(Long appointmentsId, LocalDate appointmentsDate, UUID doctorId, UUID patientId, String appointmentsDescription) {
        this.appointmentsId = appointmentsId;
        this.appointmentsDate = appointmentsDate;
        this.doctorId = doctorId;
        this.patientId = patientId;
        this.appointmentsDescription = appointmentsDescription;
    }

    public Long getAppointmentsId() {
        return appointmentsId;
    }

    public void setAppointmentsId(Long appointmentsId) {
        this.appointmentsId = appointmentsId;
    }

    public LocalDate getAppointmentsDate() {
        return appointmentsDate;
    }

    public void setAppointmentsDate(LocalDate appointmentsDate) {
        this.appointmentsDate = appointmentsDate;
    }

    public UUID getDoctorId() {
        return doctorId;
    }

    public void setDoctorId(UUID doctorId) {
        this.doctorId = doctorId;
    }

    public UUID getPatientId() {
        return patientId;
    }

    public void setPatientId(UUID patientId) {
        this.patientId = patientId;
    }

    public String getAppointmentsDescription() {
        return appointmentsDescription;
    }

    public void setAppointmentsDescription(String appointmentsDescription) {
        this.appointmentsDescription = appointmentsDescription;
    }
}
