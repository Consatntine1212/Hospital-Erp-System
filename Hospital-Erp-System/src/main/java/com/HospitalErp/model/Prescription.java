package com.HospitalErp.model;

import jakarta.persistence.*;
import org.hibernate.annotations.GenericGenerator;

import java.time.LocalDate;
import java.util.Optional;
import java.util.Set;

@Entity
@Table(name = "prescriptions")
public class Prescription {
    @Id
    @GeneratedValue(strategy= GenerationType.AUTO,generator="native")
    @GenericGenerator(name = "native",strategy = "native")
    @Column(name = "prescription_id")
    private Long prescriptionId;

    @Column(name = "prescriptions_summary")
    private String prescriptionsSummary;

    @ManyToOne
    @JoinColumn(name = "doctor_id", referencedColumnName = "user_id")
    private User doctor;

    @ManyToOne
    @JoinColumn(name = "patient_id", referencedColumnName = "user_id")
    private User patient;

    @Column(name = "create_dt")
    private LocalDate createDt = LocalDate.now();

    @Column(name = "expiration_dt")
    private LocalDate expirationDt = LocalDate.now().plusMonths(1);

    @Column(name = "fulfillement_dt")
    private LocalDate fulfillmentDt;

    @Column(name = "status")
    private String status = "Pending";

    @ManyToMany
    @JoinTable(
            name = "prescription_drugs",
            joinColumns = @JoinColumn(name = "prescription_id"),
            inverseJoinColumns = @JoinColumn(name = "drug_id")
    )
    private Set<Drug> drugs;

    // Getters and setters


    public Long getPrescriptionId() {
        return prescriptionId;
    }

    public void setPrescriptionId(Long prescriptionId) {
        this.prescriptionId = prescriptionId;
    }

    public String getPrescriptionsSummary() {
        return prescriptionsSummary;
    }

    public void setPrescriptionsSummary(String prescriptionsSummary) {
        this.prescriptionsSummary = prescriptionsSummary;
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

    public LocalDate getCreateDt() {
        return createDt;
    }

    public void setCreateDt(LocalDate createDt) {
        this.createDt = createDt;
    }

    public LocalDate getExpirationDt() {
        return expirationDt;
    }

    public void setExpirationDt(LocalDate expirationDt) {
        this.expirationDt = expirationDt;
    }

    public LocalDate getFulfillmentDt() {
        return fulfillmentDt;
    }

    public void setFulfillmentDt(LocalDate fulfillmentDt) {
        this.fulfillmentDt = fulfillmentDt;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Set<Drug> getDrugs() {
        return drugs;
    }

    public void setDrugs(Set<Drug> drugs) {
        this.drugs = drugs;
    }
}
