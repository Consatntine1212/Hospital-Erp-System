package com.HospitalErp.model;

import jakarta.persistence.*;

@Entity
@Table(name = "doctor_patient")
public class DoctorPatient {

    @Id
    @ManyToOne
    @JoinColumn(name = "doctor_id")
    private User doctor;

    @Id
    @ManyToOne
    @JoinColumn(name = "patient_id")
    private User patient;

    // Constructors
    public DoctorPatient() {}

    public DoctorPatient(User doctor, User patient) {
        this.doctor = doctor;
        this.patient = patient;
    }

    // Getters and Setters
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
}