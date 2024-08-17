package com.HospitalErp.DTO;

import java.util.UUID;

public class UserRegistrationDTO {
    private UUID id;
    private String password;
    private String firstName;
    private String lastName;
    private String email;
    private String mobileNumber;
    private String role;
    private String gender;
    private String status;
    private String iconFileName;
    private String description;
    private String doctorSpecialization;
    private String officeNo;
    private boolean isMyPatient;

    // Constructor
// Default constructor
    public UserRegistrationDTO() {
    }

    public UserRegistrationDTO(UUID id, String firstName, String lastName, String mobileNumber, String status, String iconFileName, String description, String doctorSpecialization, String officeNo) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.mobileNumber = mobileNumber;
        this.status = status;
        this.iconFileName = iconFileName;
        this.description = description;
        this.doctorSpecialization = doctorSpecialization;
        this.officeNo = officeNo;
    }

    public UserRegistrationDTO(UUID id, String password, String firstName, String lastName, String email, String mobileNumber, String role, String gender, String iconFileName, String description, String doctorSpecialization, String officeNo) {
        this.id = id;
        this.password = password;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.mobileNumber = mobileNumber;
        this.role = role;
        this.gender = gender;
        this.iconFileName = iconFileName;
        this.description = description;
        this.doctorSpecialization = doctorSpecialization;
        this.officeNo = officeNo;
    }

    public UserRegistrationDTO(UUID id, String password, String firstName, String lastName, String email, String mobileNumber, String role, String gender) {
        this.id = id;
        this.password = password;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.mobileNumber = mobileNumber;
        this.role = role;
        this.gender = gender;
    }

    public UserRegistrationDTO(UUID id, String email) {
        this.id = id;
        this.email = email;
    }

    public UserRegistrationDTO(String email) {
        this.email = email;
    }

    public UserRegistrationDTO(UUID uuid, String firstName, String lastName, String email, String mobileNumber, String role, String gender, Boolean isMyPatient) {
        this.id = uuid;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.mobileNumber = mobileNumber;
        this.role = role;
        this.gender = gender;
        this.isMyPatient = isMyPatient;
    }

    // Getters and Setters


    public String getIconFileName() {
        return iconFileName;
    }

    public void setIconFileName(String iconFileName) {
        this.iconFileName = iconFileName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
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

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getMobileNumber() {
        return mobileNumber;
    }

    public void setMobileNumber(String mobileNumber) {
        this.mobileNumber = mobileNumber;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public boolean isMyPatient() {
        return isMyPatient;
    }

    public void setMyPatient(boolean myPatient) {
        isMyPatient = myPatient;
    }
}
