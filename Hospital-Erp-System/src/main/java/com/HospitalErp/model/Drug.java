package com.HospitalErp.model;

import jakarta.persistence.*;
import java.util.Set;
import org.hibernate.annotations.GenericGenerator;

@Entity
@Table(name = "drugs")
public class Drug {
    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    @Column(name = "drug_id")
    private Long drugId;

    @Column(name = "drug_market_name")
    private String drugMarketName;

    @Column(name = "drug_desc")
    private String drugDesc;

    @Column(name = "delivery_method")
    private String deliveryMethod;

    @Column(name = "strength")
    private String strength;

    @Column(name = "price")
    private Long price;

    @Column(name = "active_ingredients")
    private String activeIngredients;

    @ManyToOne
    @JoinColumn(name = "manufacturer_id")
    private Manufacturer manufacturer;


    // Getters and Setters


    public Long getDrugId() {
        return drugId;
    }

    public void setDrugId(Long drugId) {
        this.drugId = drugId;
    }

    public String getDrugMarketName() {
        return drugMarketName;
    }

    public void setDrugMarketName(String drugMarketName) {
        this.drugMarketName = drugMarketName;
    }

    public String getDrugDesc() {
        return drugDesc;
    }

    public void setDrugDesc(String drugDesc) {
        this.drugDesc = drugDesc;
    }

    public String getDeliveryMethod() {
        return deliveryMethod;
    }

    public void setDeliveryMethod(String deliveryMethod) {
        this.deliveryMethod = deliveryMethod;
    }

    public String getStrength() {
        return strength;
    }

    public void setStrength(String strength) {
        this.strength = strength;
    }

    public Long getPrice() {
        return price;
    }

    public void setPrice(Long price) {
        this.price = price;
    }

    public String getActiveIngredients() {
        return activeIngredients;
    }

    public void setActiveIngredients(String activeIngredients) {
        this.activeIngredients = activeIngredients;
    }

    public Manufacturer getManufacturer() {
        return manufacturer;
    }

    public void setManufacturer(Manufacturer manufacturer) {
        this.manufacturer = manufacturer;
    }

}

