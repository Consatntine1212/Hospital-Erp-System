package com.HospitalErp.model;

import jakarta.persistence.*;
import org.hibernate.annotations.GenericGenerator;

@Entity
@Table(name = "drug_manufacturers")
public class Manufacturer {
    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    @Column(name = "manufacturer_id")
    private Long manufacturerId;

    @Column(name = "manufacturer")
    private String manufacturerName;

    @Column(name = "manufacturer_desc")
    private String manufacturerDesc;

    // Getters and Setters

    public Long getManufacturerId() {
        return manufacturerId;
    }

    public void setManufacturerId(Long manufacturerId) {
        this.manufacturerId = manufacturerId;
    }

    public String getManufacturerName() {
        return manufacturerName;
    }

    public void setManufacturerName(String manufacturerName) {
        this.manufacturerName = manufacturerName;
    }

    public String getManufacturerDesc() {
        return manufacturerDesc;
    }

    public void setManufacturerDesc(String manufacturerDesc) {
        this.manufacturerDesc = manufacturerDesc;
    }
}
