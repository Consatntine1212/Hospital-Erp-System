package com.HospitalErp.Request_Models;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalTime;

public class CreateDoctorScheduleRequest {
    private Long id;
    private LocalDate date;
    private DayOfWeek dayOfWeek;
    private Long timeSlotId;
    private Boolean isAvailable;
    private LocalTime startTime;
    // Constructors, getters, and setters
    // Default constructor
    public CreateDoctorScheduleRequest() {
    }


    public CreateDoctorScheduleRequest(Long id, DayOfWeek dayOfWeek, Boolean isAvailable, LocalTime startTime) {
        this.id = id;
        this.dayOfWeek = dayOfWeek;
        this.isAvailable = isAvailable;
        this.startTime = startTime;
    }

    public CreateDoctorScheduleRequest(LocalDate date, DayOfWeek dayOfWeek, Long timeSlotId) {
        this.date = date;
        this.dayOfWeek = dayOfWeek;
        this.timeSlotId = timeSlotId;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public DayOfWeek getDayOfWeek() {
        return dayOfWeek;
    }

    public void setDayOfWeek(DayOfWeek dayOfWeek) {
        this.dayOfWeek = dayOfWeek;
    }

    public Long getTimeSlotId() {
        return timeSlotId;
    }

    public void setTimeSlotId(Long timeSlotId) {
        this.timeSlotId = timeSlotId;
    }

    public Boolean getIsAvailable() {
        return isAvailable;
    }

    public void setIsAvailable(Boolean available) {
        isAvailable = available;
    }

    public LocalTime getStartTime() {
        return startTime;
    }

    public void setStartTime(LocalTime startTime) {
        this.startTime = startTime;
    }
}
