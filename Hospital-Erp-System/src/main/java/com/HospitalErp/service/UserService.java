package com.HospitalErp.service;

import com.HospitalErp.DTO.UpdateUserPatientsDTO;
import com.HospitalErp.model.User;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface UserService {
    Optional<User> getUserById(UUID uuid);
    Optional<User> getUserByEmail(String Email);
    List<Object[]> getPatientByDoctorId(String UUID);
    List<User> getAllPatients();
    List<User> getAllDoctors();
    User saveUser(User user);
    void updateUserPatients(UUID userId, UpdateUserPatientsDTO dto);

}
