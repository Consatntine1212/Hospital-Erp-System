package com.HospitalErp.DTO;

import com.HospitalErp.model.DoctorSchedule;
import com.HospitalErp.model.User;
import jakarta.persistence.*;
import org.hibernate.annotations.GenericGenerator;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.UUID;

public class AppointmentDTO {
    private Long appointmentsId;
    private LocalDate appointmentsDate;
    private LocalTime startTime;
    private DayOfWeek dayOfWeek;
    private UUID doctorId;;
    private String doctorFirstName;
    private String doctorLastName;
    private UUID patientId;
    private String patientFirstName;
    private String patientLastName;
    private String appointmentsDescription;
    private LocalDate createDt;
    private String doctorSpecialization;
    private String officeNo;

    public AppointmentDTO(Long appointmentsId, LocalDate appointmentsDate, LocalTime startTime, DayOfWeek dayOfWeek, UUID doctorId, String doctorFirstName, String doctorLastName, UUID patientId, String appointmentsDescription, LocalDate createDt, String doctorSpecialization, String officeNo) {
        this.appointmentsId = appointmentsId;
        this.appointmentsDate = appointmentsDate;
        this.startTime = startTime;
        this.dayOfWeek = dayOfWeek;
        this.doctorId = doctorId;
        this.doctorFirstName = doctorFirstName;
        this.doctorLastName = doctorLastName;
        this.patientId = patientId;
        this.appointmentsDescription = appointmentsDescription;
        this.createDt = createDt;
        this.doctorSpecialization = doctorSpecialization;
        this.officeNo = officeNo;
    }

    public AppointmentDTO() {}

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

    public LocalTime getStartTime() {
        return startTime;
    }

    public void setStartTime(LocalTime startTime) {
        this.startTime = startTime;
    }

    public DayOfWeek getDayOfWeek() {
        return dayOfWeek;
    }

    public void setDayOfWeek(DayOfWeek dayOfWeek) {
        this.dayOfWeek = dayOfWeek;
    }

    public UUID getDoctorId() {
        return doctorId;
    }

    public void setDoctorId(UUID doctorId) {
        this.doctorId = doctorId;
    }

    public String getDoctorFirstName() {
        return doctorFirstName;
    }

    public void setDoctorFirstName(String doctorFirstName) {
        this.doctorFirstName = doctorFirstName;
    }

    public String getDoctorLastName() {
        return doctorLastName;
    }

    public void setDoctorLastName(String doctorLastName) {
        this.doctorLastName = doctorLastName;
    }

    public UUID getPatientId() {
        return patientId;
    }

    public void setPatientId(UUID patientId) {
        this.patientId = patientId;
    }

    public String getPatientFirstName() {
        return patientFirstName;
    }

    public void setPatientFirstName(String patientFirstName) {
        this.patientFirstName = patientFirstName;
    }

    public String getPatientLastName() {
        return patientLastName;
    }

    public void setPatientLastName(String patientLastName) {
        this.patientLastName = patientLastName;
    }

    public String getAppointmentsDescription() {
        return appointmentsDescription;
    }

    public void setAppointmentsDescription(String appointmentsDescription) {
        this.appointmentsDescription = appointmentsDescription;
    }

    public LocalDate getCreateDt() {
        return createDt;
    }

    public void setCreateDt(LocalDate createDt) {
        this.createDt = createDt;
    }

    public String getDoctorSpecialization() {
        return doctorSpecialization;
    }

    public void setDoctorSpecialization(String doctorSpecialization) {
        this.doctorSpecialization = doctorSpecialization;
    }

    public String getOfficeNo() {
        return officeNo;
    }

    public void setOfficeNo(String officeNo) {
        this.officeNo = officeNo;
    }
}
