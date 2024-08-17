package com.HospitalErp;

import com.HospitalErp.Email.EmailService;
import com.HospitalErp.model.Authority;
import com.HospitalErp.model.User;
import com.HospitalErp.model.VerificationToken;
import com.HospitalErp.repository.DrugRepository;
import com.HospitalErp.repository.UserRepository;
import com.HospitalErp.repository.VerificationTokenRepository;
import com.HospitalErp.service.UserService;
import com.HospitalErp.service.VerificationTokenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;

@SpringBootApplication
public class HospitalErpSystemApplication implements CommandLineRunner {

    @Autowired
    private EmailService emailService;

    @Autowired
    private UserService userService;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private VerificationTokenRepository verificationTokenRepository;

    @Autowired
    private VerificationTokenService verificationTokenService;

    public static void main(String[] args) {
        SpringApplication.run(HospitalErpSystemApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
        /*
        List<User> users = (List<User>) userRepository.findAll();
        users.forEach(user -> {
            System.out.println("user id : "+user.getId());
        });
        System.out.println();
        System.out.println();

        List<VerificationToken> tokens = (List<VerificationToken>) verificationTokenRepository.findAll();
        tokens.forEach(token -> {
            System.out.println("token id : "+token.getId());
        });
        Optional<User> optionaluser = userRepository.findById(UUID.fromString("6bd9b0f4-4912-11ef-b6bd-98597a8a591b"));
        User user = optionaluser.get();
        Set<Authority> authorytes = user.getAuthorities();
// Print the authorities
        for (Authority authority : authorytes) {
            System.out.println(authority.getName());
        }*/
    }
}
