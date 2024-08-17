package com.HospitalErp.serviceImplementation;


import com.HospitalErp.model.Account;
import com.HospitalErp.repository.AccountRepository;
import com.HospitalErp.repository.AppointmentRepository;
import com.HospitalErp.service.AccountService;
import com.HospitalErp.service.AppointmentService;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class AccountServiceImplementation implements AccountService
{
    private final AccountRepository accountRepository;

    public AccountServiceImplementation(AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }

    @Override
    public Account findAccountByUserId(UUID id) {
        return accountRepository.findByUserId(id);
    }
}
