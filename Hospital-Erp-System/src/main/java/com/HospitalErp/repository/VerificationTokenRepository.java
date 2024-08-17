package com.HospitalErp.repository;

import com.HospitalErp.model.User;
import com.HospitalErp.model.VerificationToken;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface VerificationTokenRepository extends CrudRepository<VerificationToken, UUID> {
}
