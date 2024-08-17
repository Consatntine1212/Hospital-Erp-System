package com.HospitalErp.serviceImplementation;

import com.HospitalErp.model.TimeSlot;
import com.HospitalErp.repository.TimeSlotRepository;
import com.HospitalErp.service.TimeSlotService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TimeSlotServiceImplementation implements TimeSlotService {

    private final TimeSlotRepository timeSlotRepository;

    @Autowired
    public TimeSlotServiceImplementation(TimeSlotRepository timeSlotRepository) {
        this.timeSlotRepository = timeSlotRepository;
    }

    @Override
    public TimeSlot createTimeSlot(TimeSlot timeSlot) {
        // Add any necessary business logic here
        return timeSlotRepository.save(timeSlot);
    }

    @Override
    public TimeSlot getTimeSlotById(Long TimeSlotId) {
        return timeSlotRepository.findById(TimeSlotId).orElse(null);
    }

}
