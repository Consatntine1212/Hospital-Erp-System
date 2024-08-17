package com.HospitalErp.service;

import com.HospitalErp.model.Account;
import com.HospitalErp.model.Appointment;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface AccountService {

Account findAccountByUserId(UUID id);

}