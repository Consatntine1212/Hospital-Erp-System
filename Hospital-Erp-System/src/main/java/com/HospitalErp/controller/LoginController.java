package com.HospitalErp.controller;

import com.HospitalErp.DTO.UpdateUserPatientsDTO;
import com.HospitalErp.DTO.UserRegistrationDTO;
import com.HospitalErp.Email.EmailService;
import com.HospitalErp.model.Account;
import com.HospitalErp.model.User;
import com.HospitalErp.model.VerificationToken;
import com.HospitalErp.repository.AccountRepository;
import com.HospitalErp.repository.UserRepository;
import com.HospitalErp.service.AccountService;
import com.HospitalErp.service.UserService;
import com.HospitalErp.service.VerificationTokenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.sql.Date;
import java.time.LocalDate;
import java.util.*;

@RestController
public class LoginController {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private AccountRepository accountRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private UserService userService;
    @Autowired
    private VerificationTokenService verificationTokenService;
    @Autowired
    private EmailService emailService;
    @Autowired
    private AccountService accountService;

    @PostMapping("/register")
    public ResponseEntity<Object> registerUser(@RequestBody User registrationDto) {
        User savedUser = null;
        try {
            String hashPwd = passwordEncoder.encode(registrationDto.getPassword());
            User user = new User();
            user.setFirstName(registrationDto.getFirstName());
            user.setLastName(registrationDto.getLastName());
            user.setEmail(registrationDto.getEmail());
            user.setMobileNumber(registrationDto.getMobileNumber());
            user.setPassword(hashPwd);
            user.setRole("USER");// Set default role or get from registrationDto
            user.setRole(registrationDto.getRole());// Set default role or get from registrationDto
            user.setGender(registrationDto.getGender());
            user.setCreateDt(String.valueOf(new Date(System.currentTimeMillis())));
            user.setStatus("Pending");

            savedUser = userRepository.save(user);

            // Create and set default values for ProfileInfo
            Account account = new Account();
            account.setUser(savedUser);
            account.setIconFileName("default.png"); // Default icon file name
            account.setDescription("No description provided");
            account.setDoctorSpecialization("General practice ");
            account.setOfficeNo("N/A");
            accountRepository.save(account);

            // Create and send the verification token
            LocalDate tomorrow = LocalDate.now().plusDays(1);
            VerificationToken token = new VerificationToken();
            token.setUser(savedUser);
            token.setExpirationDt(tomorrow);
            verificationTokenService.newToken(token);
            emailService.sendRegistrationConfirmation(user.getEmail(), user.getFirstName() + " " + user.getLastName(), token.getId());

            return ResponseEntity.status(HttpStatus.CREATED)
                    .body(Map.of("message", "User was successfully registered"));

        } catch (Exception ex) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Map.of("message", "Failed to register user"));
        }
    }

    @RequestMapping("/user")
    public User getUserDetailsAfterLogin(Authentication authentication) {
        Optional<User> optionalCustomer = userRepository.findByEmail(authentication.getName());
        return optionalCustomer.orElse(null);
    }
    @RequestMapping("/patients")
    public List<UserRegistrationDTO> getDoctorsPatientDetails(@RequestParam String id) {
        List<Object[]> users = userService.getPatientByDoctorId(id);
        List<UserRegistrationDTO> patientList = new ArrayList<>();
        for (Object[] user : users) {
            UUID userId = UUID.fromString((String) user[0]); // Assuming the first element is user_id
            String firstName = (String) user[1]; // Assuming the second element is first_name
            String lastName = (String) user[2]; // Assuming the third element is last_name
            String email = (String) user[3]; // Assuming the fourth element is email
            String mobileNumber = (String) user[4]; // Assuming the fifth element is mobile_number
            String role = (String) user[5]; // Assuming the sixth element is role
            String gender = (String) user[6]; // Assuming the seventh element is gender
            Boolean isMyPatient = ((long) user[7] == 1); // Assuming the eighth element is isMyPatient

            // Create a new UserRegistrationDTO
            UserRegistrationDTO patient = new UserRegistrationDTO(userId, firstName, lastName, email, mobileNumber, role, gender, isMyPatient);

            // Add the DTO to the list
            patientList.add(patient);
        }
        if( !patientList.isEmpty()) {
            return patientList;//new ResponseEntity<>(users, HttpStatus.OK);
        }
        else {
            return null;//new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping("/resetEmailRequest")
    public ResponseEntity<Object> resetEmailRequest(@RequestBody UserRegistrationDTO userDTO) {
        System.out.println("the email is " + userDTO.getEmail());
        LocalDate tomorrow = LocalDate.now().plusDays(1);
        try {
            Optional<User> optionalUser = userService.getUserByEmail(userDTO.getEmail());
            if (optionalUser.isPresent()) {
                User user = optionalUser.get();
                VerificationToken token = new VerificationToken();
                //token.setId(UUID.randomUUID());
                System.out.println("the token is " + token.getId());
                token.setUser(user);
                token.setExpirationDt(tomorrow);
                verificationTokenService.newToken(token);
                emailService.sendResetEmail(user.getEmail(),token.getId(),user.getFirstName()+" " + user.getLastName());
                return ResponseEntity.status(HttpStatus.CREATED)
                        .body(Map.of("message", "Email was successfully send"));
            }
        } catch (Exception ex) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Map.of("message", "Failed to send email"));
        }
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(Map.of("message", "Failed to send email"));
    }
    @PostMapping("/changePassword")
    public ResponseEntity<Object> changePassword( @RequestBody UserRegistrationDTO request){
        UUID actualUuid = request.getId();
        String newPassword = request.getPassword();
        Optional<VerificationToken> token = verificationTokenService.findById(actualUuid);
        LocalDate now  = LocalDate.now();
        try {
            if (token.isPresent()) {
                User user = token.get().getUser();
                LocalDate expirationDt = token.get().getExpirationDt();
                if (user.getId() != null) {
                    if (!expirationDt.isBefore(now)) {
                        String hashPwd = passwordEncoder.encode(newPassword);
                        user.setPassword(hashPwd);
                        userService.saveUser(user);
                        return ResponseEntity.status(HttpStatus.CREATED)
                                .body(Map.of("message", "Password changed successfully"));
                    }
                    return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                            .body(Map.of("message", "The expiration date has passed"));
                }
            }
        } catch (Exception ex) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Map.of("message", "Failed to change password"));
        }
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(Map.of("message", "Failed to change password"));
    }
    @PostMapping("/updateUserAccount")
    public ResponseEntity<Object> updateUserAcount( @RequestBody UserRegistrationDTO userDTO){
        Optional<User> optionalUser = userService.getUserById(userDTO.getId());
        Account account = accountService.findAccountByUserId(userDTO.getId());
        try {
            if (optionalUser.isPresent()) {
                User user = optionalUser.get();
                if(userDTO.getFirstName() != null){
                    user.setFirstName(userDTO.getFirstName());
                }
                if(userDTO.getLastName() != null){
                    user.setLastName(userDTO.getLastName());
                }
                if(userDTO.getMobileNumber() != null){
                    user.setMobileNumber(userDTO.getMobileNumber());
                }
                if(userDTO.getStatus() != null){
                    user.setStatus(userDTO.getStatus());
                }
                if(userDTO.getDoctorSpecialization() != null){
                    account.setDoctorSpecialization(userDTO.getDoctorSpecialization());
                }
                if(userDTO.getOfficeNo() != null){
                    account.setOfficeNo(userDTO.getOfficeNo());
                }
                if(userDTO.getDescription() != null){
                    account.setDescription(userDTO.getDescription());
                }
                if(userDTO.getIconFileName() != null){
                    account.setIconFileName(userDTO.getIconFileName());
                }
                accountRepository.save(account);
                userService.saveUser(user);
                return ResponseEntity.status(HttpStatus.CREATED)
                        .body(Map.of("message", "The Account was updated successfully"));

            }
        } catch (Exception ex) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Map.of("message", "Failed to update Account"));
        }
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(Map.of("message", "Failed to update Account"));
    }
    @PostMapping("/updatePatients")
    public ResponseEntity<Object> updateUserPatients(
            @RequestParam String userId,
            @RequestBody UpdateUserPatientsDTO updateUserPatientsDTO) {
        try {
            UUID uuid = UUID.fromString(userId);
            userService.updateUserPatients(uuid, updateUserPatientsDTO);
            return ResponseEntity.status(HttpStatus.CREATED)
                    .body(Map.of("message", "Your Patients were updated successfully"));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

}
