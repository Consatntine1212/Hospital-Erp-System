package com.HospitalErp.service;


import com.HospitalErp.model.Drug;

import java.util.List;
import java.util.Optional;

public interface DrugService {
    Drug updateDrug(Long AppointmentId, Drug updatedAppointment);
    List<Drug> getAllDrugs();
    List<Drug> getAllDrugsWithCount();

    Optional<Drug> getDrugById(Long AppointmentId);

}
