package com.HospitalErp.DTO;

import java.util.List;
import java.util.UUID;

public class SimplePrescriptionDTO {
    private Long prescriptionId;
    private String prescriptionsSummary;
    private String status;
    private UUID doctorId;
    private UUID patientId;
    private List<Long> drugIds;

    @Override
    public String toString() {
        return "SimplePrescriptionDTO{" +
                "prescriptionId=" + prescriptionId +
                ", prescriptionsSummary='" + prescriptionsSummary + '\'' +
                ", status='" + status + '\'' +
                ", doctorId=" + doctorId +
                ", patientId=" + patientId +
                ", drugIds=" + drugIds +
                '}';
    }

    // No-argument constructor
    public SimplePrescriptionDTO() {}

    // Parameterized constructors
    public SimplePrescriptionDTO(String prescriptionsSummary, String status, UUID doctorId, UUID patientId, List<Long> drugIds) {
        this.prescriptionsSummary = prescriptionsSummary;
        this.status = status;
        this.doctorId = doctorId;
        this.patientId = patientId;
        this.drugIds = drugIds;
    }

    public SimplePrescriptionDTO(Long prescriptionId, String prescriptionsSummary, String status, UUID doctorId, UUID patientId, List<Long> drugIds) {
        this.prescriptionId = prescriptionId;
        this.prescriptionsSummary = prescriptionsSummary;
        this.status = status;
        this.doctorId = doctorId;
        this.patientId = patientId;
        this.drugIds = drugIds;
    }

    // Getters and Setters
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

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
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

    public List<Long> getDrugIds() {
        return drugIds;
    }

    public void setDrugIds(List<Long> drugIds) {
        this.drugIds = drugIds;
    }
}
