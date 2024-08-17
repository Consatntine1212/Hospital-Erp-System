package com.HospitalErp.serviceImplementation;


import com.HospitalErp.model.DoctorSchedule;
import com.HospitalErp.repository.DoctorScheduleRepository;
import com.HospitalErp.service.DoctorScheduleService;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.UUID;

@Service
public class DoctorScheduleServiceImplementation implements DoctorScheduleService {
    private final DoctorScheduleRepository doctorScheduleRepository;

    public DoctorScheduleServiceImplementation(DoctorScheduleRepository doctorScheduleRepository) {
        this.doctorScheduleRepository = doctorScheduleRepository;
    }


    @Override
    public DoctorSchedule updateDoctorSchedule(UUID doctorId, RequestBody requestBody) {
        return null;
    }

    public List<DoctorSchedule> getDoctorSchedules(UUID doctorId) {
        return doctorScheduleRepository.findByDoctorId(doctorId);
    }

    @Override
    public List<DoctorSchedule> getActiveDoctorSchedules(UUID doctorId) {
        return doctorScheduleRepository.findAllActiveSchedule(doctorId);
    }


    public DoctorSchedule createSchedule(DoctorSchedule newSchedule) {
        return doctorScheduleRepository.save(newSchedule);
    }

    @Override
    public DoctorSchedule getByScheduleID(Long scheduleId) {
        Optional<DoctorSchedule> optional = doctorScheduleRepository.findById(scheduleId);
        if(optional.isPresent()){
            return optional.get();
        }
        else {
            throw new NoSuchElementException("DoctorSchedule not found for schedule ID: " + scheduleId);
        }
    }

    @Override
    public void updateDoctorSchedules(DoctorSchedule newSchedule) {
        doctorScheduleRepository.save(newSchedule);
    }
}
