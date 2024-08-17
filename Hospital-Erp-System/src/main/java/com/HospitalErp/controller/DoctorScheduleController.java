package com.HospitalErp.controller;

import com.HospitalErp.Request_Models.CreateDoctorScheduleRequest;
import com.HospitalErp.model.DoctorSchedule;
import com.HospitalErp.model.TimeSlot;
import com.HospitalErp.model.User;
import com.HospitalErp.service.DoctorScheduleService;
import com.HospitalErp.serviceImplementation.DoctorScheduleServiceImplementation;
import com.HospitalErp.service.TimeSlotService;
import com.HospitalErp.serviceImplementation.UserServiceImplementation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RestController
public class DoctorScheduleController {

    private final DoctorScheduleService doctorScheduleService;

    public DoctorScheduleController(DoctorScheduleServiceImplementation doctorScheduleService) {
        this.doctorScheduleService = doctorScheduleService;
    }

    @Autowired
    private UserServiceImplementation userService;
    @Autowired
    private TimeSlotService timeSlotService;


    @GetMapping("/DoctorsSchedule")
    public List<DoctorSchedule> getDoctorSchedules(@RequestParam UUID  doctorId) {
        return doctorScheduleService.getDoctorSchedules(doctorId);
    }
    @GetMapping("/Schedule")
    public DoctorSchedule getSchedules(@RequestParam long id) {
        return doctorScheduleService.getByScheduleID(id);
    }

    @PostMapping("/UpdateSchedule")
    public ResponseEntity<DoctorSchedule> updateDoctorSchedule(
            @RequestParam UUID doctorId,
            @RequestBody CreateDoctorScheduleRequest request) {
        // Retrieve the Schedule by ID
        try {
            DoctorSchedule oldSchedule =doctorScheduleService.getByScheduleID(request.getId());
            oldSchedule.setAvailable(request.getIsAvailable());
            doctorScheduleService.updateDoctorSchedules(oldSchedule);
            return ResponseEntity.ok(oldSchedule);
        }
        catch (Exception e) {
            // Log the exception and return 500 Internal Server Error
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }


    @PostMapping("CreateNewSchedules")
    public ResponseEntity<DoctorSchedule> createDoctorSchedule(
            @RequestParam UUID doctorId,
            @RequestBody CreateDoctorScheduleRequest request)  {

        // Retrieve the doctor by ID
        Optional<User> doctorOptional  = userService.getUserById(doctorId);
        if (doctorOptional.isEmpty()) {
            return ResponseEntity.ok(new DoctorSchedule());
        }
        // Retrieve the timeSlot by ID
        TimeSlot timeSlot = timeSlotService.getTimeSlotById(request.getTimeSlotId());
        if (timeSlot == null) {
            return ResponseEntity.ok(new DoctorSchedule());
        }

        // Create a new schedule entity
        DoctorSchedule newSchedule = new DoctorSchedule();
        doctorOptional.ifPresent(newSchedule::setDoctor);
        newSchedule.setDayOfWeek(request.getDayOfWeek());
        newSchedule.setTimeSlot(timeSlot);

        // Save the schedule to the database
        DoctorSchedule createdSchedule = doctorScheduleService.createSchedule(newSchedule);

        // Return the newly created schedule in the response
        return ResponseEntity.status(HttpStatus.CREATED).body(createdSchedule);


    }
}
