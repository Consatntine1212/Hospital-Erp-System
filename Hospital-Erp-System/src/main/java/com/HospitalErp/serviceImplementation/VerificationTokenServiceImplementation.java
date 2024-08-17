package com.HospitalErp.serviceImplementation;

import ch.qos.logback.core.subst.Token;
import com.HospitalErp.model.VerificationToken;
import com.HospitalErp.repository.UserRepository;
import com.HospitalErp.repository.VerificationTokenRepository;
import com.HospitalErp.service.VerificationTokenService;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Service
public class VerificationTokenServiceImplementation implements VerificationTokenService {
    private final VerificationTokenRepository verificationTokenRepository;

    public VerificationTokenServiceImplementation(VerificationTokenRepository verificationTokenRepository) {
        this.verificationTokenRepository = verificationTokenRepository;
    }

    @Override
    public void newToken(VerificationToken token) {
        verificationTokenRepository.save(token);
    }

    @Override
    public Optional<VerificationToken> findById(UUID uuid) {
        return  verificationTokenRepository.findById(uuid);
    }
}
