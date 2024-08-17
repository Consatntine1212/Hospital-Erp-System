package com.HospitalErp.controller;

import com.HospitalErp.model.Drug;
import com.HospitalErp.service.DrugService;
import org.springframework.http.CacheControl;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.concurrent.TimeUnit;

@RestController
public class DrugController {
    private final DrugService drugService;

    public DrugController(DrugService drugService) {
        this.drugService = drugService;
    }


    @GetMapping("/myAvailableDrugs")
    public ResponseEntity<List<Drug>> getDrugsDetailsWithInstanceCount() {
        List<Drug> drugsWithInstanceCount = drugService.getAllDrugsWithCount();
        if (!drugsWithInstanceCount.isEmpty()) {
            return ResponseEntity.ok()
                    .cacheControl(CacheControl.maxAge(60, TimeUnit.SECONDS))
                    .body(drugsWithInstanceCount);
        } else {
            return ResponseEntity.notFound().build();
        }
    }
    @GetMapping("/myDrugs")
    public ResponseEntity<List<Drug>> getDrugsDetails() {
        List<Drug> drugsWithInstanceCount = drugService.getAllDrugsWithCount();
        if (!drugsWithInstanceCount.isEmpty()) {
            return ResponseEntity.ok()
                    .cacheControl(CacheControl.maxAge(60, TimeUnit.SECONDS))
                    .body(drugsWithInstanceCount);
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
