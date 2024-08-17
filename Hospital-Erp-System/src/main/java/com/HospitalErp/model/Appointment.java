package com.HospitalErp.model;

import jakarta.persistence.*;
import org.hibernate.annotations.GenericGenerator;

import java.sql.Date;
import java.time.LocalDate;
@Entity
@Table(name = "appointments")
public class Appointment {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO, generator = "native")
	@GenericGenerator(name = "native", strategy = "native")
	@Column(name = "appointments_id")
	private Long appointmentsId;

	@Column(name = "appointments_date")
	private LocalDate appointmentsDate;

	@ManyToOne
	@JoinColumn(name = "schedule_id", referencedColumnName = "schedule_id")
	private DoctorSchedule schedule;

	@ManyToOne
	@JoinColumn(name = "doctor_id", referencedColumnName = "user_id")
	private User doctor;

	@ManyToOne
	@JoinColumn(name = "patient_id", referencedColumnName = "user_id")
	private User patient;

	@Column(name = "appointments_description")
	private String appointmentsDescription;

	@Column(name = "create_dt")
	private LocalDate createDt;

	// Getters and setters

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

	public DoctorSchedule getSchedule() {
		return schedule;
	}

	public void setSchedule(DoctorSchedule schedule) {
		this.schedule = schedule;
	}

	public User getDoctor() {
		return doctor;
	}

	public void setDoctor(User doctor) {
		this.doctor = doctor;
	}

	public User getPatient() {
		return patient;
	}

	public void setPatient(User patient) {
		this.patient = patient;
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

}