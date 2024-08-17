package com.HospitalErp.serviceImplementation;

import com.HospitalErp.model.Drug;
import com.HospitalErp.repository.DrugRepository;
import com.HospitalErp.service.DrugService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class DrugServiceImplementation implements DrugService {
    private final DrugRepository drugRepository;


    public DrugServiceImplementation(DrugRepository drugRepository) {
        this.drugRepository = drugRepository;
    }

    @Override
    public Drug updateDrug(Long AppointmentId, Drug updatedAppointment) {
        return null;
    }

    @Override
    public List<Drug> getAllDrugs() {
        return drugRepository.findAll();
    }

    @Override
    public List<Drug> getAllDrugsWithCount() {
        return drugRepository.findAll();
    }

    @Override
    public Optional<Drug> getDrugById(Long DrugId) {
        return drugRepository.findById(DrugId);
    }
}
