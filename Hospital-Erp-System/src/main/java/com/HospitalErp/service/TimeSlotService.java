package com.HospitalErp.service;

import com.HospitalErp.model.TimeSlot;

public interface TimeSlotService {
    TimeSlot createTimeSlot(TimeSlot timeSlot);
    TimeSlot getTimeSlotById(Long TimeSlotId);

}
