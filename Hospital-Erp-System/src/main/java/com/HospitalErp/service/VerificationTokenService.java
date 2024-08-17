package com.HospitalErp.service;

import com.HospitalErp.model.VerificationToken;

import java.util.Optional;
import java.util.UUID;

public interface VerificationTokenService {
    void newToken(VerificationToken token);
    Optional<VerificationToken> findById(UUID uuid);
}
