package com.HospitalErp.DTO;
import java.util.Set;
import java.util.UUID;

public class UpdateUserPatientsDTO {
    private Set<UUID> patientIds;

    // Getters and Setters
    public Set<UUID> getPatientIds() {
        return patientIds;
    }

    public void setPatientIds(Set<UUID> patientIds) {
        this.patientIds = patientIds;
    }
}