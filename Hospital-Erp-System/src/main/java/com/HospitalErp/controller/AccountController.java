package com.HospitalErp.controller;


import com.HospitalErp.model.Account;
import com.HospitalErp.service.AccountService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
public class AccountController {

    private final AccountService accountService;

    public AccountController(AccountService accountService) {
        this.accountService = accountService;
    }

    @GetMapping("/myAccount")
    public Account getAccountDetails(@RequestParam UUID id) {
        Account accounts = accountService.findAccountByUserId(id);
        if (accounts != null) {
            return accounts;
        } else {
            return null;
        }
    }

}
