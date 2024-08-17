package com.HospitalErp.service;

import com.HospitalErp.model.DoctorSchedule;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;
import java.util.UUID;

public interface DoctorScheduleService {
    DoctorSchedule updateDoctorSchedule(UUID doctorId, RequestBody requestBody);
    List<DoctorSchedule> getDoctorSchedules(UUID doctorId);
    List<DoctorSchedule> getActiveDoctorSchedules(UUID doctorId);
    DoctorSchedule createSchedule(DoctorSchedule newSchedule);
    DoctorSchedule getByScheduleID(Long scheduleId);
    void updateDoctorSchedules(DoctorSchedule newSchedule);
}
