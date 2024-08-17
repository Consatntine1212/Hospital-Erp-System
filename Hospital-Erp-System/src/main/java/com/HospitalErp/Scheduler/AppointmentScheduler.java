package com.HospitalErp.Scheduler;

import com.HospitalErp.model.Appointment;
import com.HospitalErp.model.DoctorSchedule;
import com.HospitalErp.model.User;
import com.HospitalErp.service.AppointmentService;
import com.HospitalErp.service.DoctorScheduleService;
import com.HospitalErp.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.lang.reflect.Array;
import java.sql.Time;
import java.time.DayOfWeek;
import java.time.LocalTime;
import java.util.ArrayList;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;

@Component
public class AppointmentScheduler {
    @Autowired
    private final  AppointmentService appointmentService;
    private final UserService userService;
    private final DoctorScheduleService doctorScheduleService;

    public AppointmentScheduler(AppointmentService appointmentService, UserService userService, DoctorScheduleService doctorScheduleService) {
        this.appointmentService = appointmentService;
        this.userService = userService;
        this.doctorScheduleService = doctorScheduleService;
    }


    @Scheduled(initialDelay = 0, fixedRate = 6000) // Runs every minute with initial delay of 0 milliseconds
    public void createDailyAppointments() {
/*
        // Logic to create appointment entries
        Appointment appointment1 = new Appointment();
        LocalDate currentDate = LocalDate.now();
        LocalDate[] nextMonth = new LocalDate[30];
        for (int i = 1; i <= 30; i++) {
            nextMonth[i -1] = currentDate.plusDays(i);
            //System.out.println("Date  : "+ nextMonth[i -1]+" Day of the week: " + nextMonth[i -1].getDayOfWeek());
        }

        List<User> doctors = new ArrayList<>();
        doctors = userService.getAllDoctors();
        for (User doctor : doctors) {
            List<DoctorSchedule> allActiveSchedule = doctorScheduleService.getActiveDoctorSchedules(doctor.getId());
            for(LocalDate date : nextMonth){
                for (DoctorSchedule schedule :allActiveSchedule){
                    if(date.getDayOfWeek() == schedule.getDayOfWeek() && !appointmentService.checkIfAppointmentExists(date.getDayOfWeek(),date,doctor.getId(),schedule.getId())){
                        Appointment appointment = new Appointment();
                        appointment.setAppointmentsDate(date);
                        appointment.setSchedule(schedule);
                        appointment.setDoctor(doctor);
                        appointment.setCreateDt(currentDate);
                        appointmentService.createAppointment(appointment);
                    }
                }
            }
        }*/
    }
}
