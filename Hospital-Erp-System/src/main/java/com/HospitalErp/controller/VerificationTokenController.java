package com.HospitalErp.controller;

import ch.qos.logback.core.subst.Token;
import com.HospitalErp.DTO.UserRegistrationDTO;
import com.HospitalErp.model.User;
import com.HospitalErp.model.VerificationToken;
import com.HospitalErp.service.UserService;
import com.HospitalErp.service.VerificationTokenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.sql.Date;
import java.time.LocalDate;
import java.util.Locale;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

@RestController
public class VerificationTokenController {
    @Autowired
    private VerificationTokenService verificationTokenService;
    @Autowired
    private UserService userService;


    @GetMapping("/confirmEmail")
    public String confirmEmail( @RequestParam String uuid ){
        UUID actualUuid = UUID.fromString(uuid);
        Optional<VerificationToken> token = verificationTokenService.findById(actualUuid);
        LocalDate now  = LocalDate.now();
        try {
            if (token.isPresent()) {
                User user = token.get().getUser();
                LocalDate expirationDt = token.get().getExpirationDt();
                System.out.println("User "+user + " expiration dt: "+expirationDt+" now: "+now);
                if (user.getId() != null) {
                    if (!expirationDt.isBefore(now)) {
                        user.setStatus("Active");
                        userService.saveUser(user);
                        return "Email Confirmed successfully";
                    }
                    return "The expiration date has passed";
                }
            }
        } catch (Exception ex) {
            return  "Failed to Confirm Email";
        }
        return  "Failed to Confirm Email";
    }
    @GetMapping("/validateToken")
    public Boolean validateToken( @RequestParam String uuid ){
        UUID actualUuid = UUID.fromString(uuid);
        Optional<VerificationToken> token = verificationTokenService.findById(actualUuid);
        LocalDate now  = LocalDate.now();
        try {
            if (token.isPresent()) {
                LocalDate expirationDt = token.get().getExpirationDt();
                if (!expirationDt.isBefore(now)) {
                    return true;
                }
            }
        } catch (Exception ex) {
            return  false;
        }
        return  false;
    }

}
