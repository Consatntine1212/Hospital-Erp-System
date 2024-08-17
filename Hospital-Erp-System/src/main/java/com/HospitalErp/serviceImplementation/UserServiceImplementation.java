package com.HospitalErp.serviceImplementation;

import com.HospitalErp.DTO.UpdateUserPatientsDTO;
import com.HospitalErp.repository.UserRepository;
import com.HospitalErp.model.User;
import com.HospitalErp.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class UserServiceImplementation implements UserService {

    private final UserRepository userRepository;

    @Autowired
    public UserServiceImplementation(UserRepository userRepository) {
        this.userRepository = userRepository;
    }


    @Override
    public Optional<User> getUserById(UUID uuid) {
        return userRepository.findById(uuid);
    }

    @Override
    public Optional<User> getUserByEmail(String Email) {
        return userRepository.findByEmail(Email);
    }

    @Override
    public List<Object[]> getPatientByDoctorId(String UUID) {
        return userRepository.findPatientsByDoctorId(UUID);
    }

    @Override
    public List<User> getAllPatients() {
        return userRepository.findAllDoctors();
    }

    @Override
    public List<User> getAllDoctors() {
        return userRepository.findAllDoctors();
    }

    @Override
    public User saveUser(User user) {
        return userRepository.save(user);
    }

    public void updateUserPatients(UUID userId, UpdateUserPatientsDTO dto) {
        Optional<User> optionalUser = userRepository.findById(userId);
        if (optionalUser.isPresent()) {
            User user = optionalUser.get();
            Set<User> patients = new HashSet<>();

            for (UUID patientId : dto.getPatientIds()) {
                Optional<User> patient = userRepository.findById(patientId);
                if (patient.isPresent()) {
                    patients.add(patient.get());
                }
            }

            user.setPatients(patients);
            userRepository.save(user); // This line will still persist the changes
        }
    }

}
