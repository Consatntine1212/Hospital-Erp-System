package com.HospitalErp.model;

public enum Role {
    DOCTOR("Doctor"),
    NURSE("Nurse"),
    PATIENT("Patient"),
    SECRETARY("Secretary"),
    PHARMACIST("Pharmacist"),
    ADMIN("Admin");
    private String displayName;

    Role(String displayName) {
        this.displayName = displayName;
    }

    public String getDisplayName() {
        return displayName;
    }
}
