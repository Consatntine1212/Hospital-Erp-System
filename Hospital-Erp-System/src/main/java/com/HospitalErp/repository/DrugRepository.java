package com.HospitalErp.repository;

import com.HospitalErp.model.Drug;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface DrugRepository extends CrudRepository<Drug, Long> {

    @Query(value = "SELECT d.*, m.manufacturer, COUNT(di.drug_instance_id) as instance_count " +
            "FROM drugs d " +
            "LEFT JOIN drug_instance di ON d.drug_id = di.drug_id " +
            "LEFT JOIN drug_manufacturers m ON d.manufacturer_id = m.manufacturer_id " +
            "WHERE di.status = 'Active' " +
            "GROUP BY d.drug_id", nativeQuery = true)
    List<Object[]> findDrugsWithActiveInstanceCountNative();

    @Query(value = "SELECT d.*, m.manufacturer " +
            "FROM drugs d " +
            "LEFT JOIN drug_manufacturers m ON d.manufacturer_id = m.manufacturer_id " , nativeQuery = true)
    List<Object[]> findDrugsCustom();
    List<Drug> findAll();
}
