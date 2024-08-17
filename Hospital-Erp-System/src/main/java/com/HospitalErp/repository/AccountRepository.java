package com.HospitalErp.repository;

import com.HospitalErp.model.Account;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;


@Repository
public interface AccountRepository extends CrudRepository<Account, Long> {
    Account findByUserId(UUID UserId);
}