package com.HospitalErp.config;

import com.HospitalErp.filter.CsrfCookieFilter;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;
import org.springframework.security.web.csrf.CookieCsrfTokenRepository;
import org.springframework.security.web.csrf.CsrfTokenRequestAttributeHandler;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;

import java.util.Collections;

@Configuration
@EnableScheduling
public class ProjectSecurityConfig {

    @Bean
    SecurityFilterChain defaultSecurityFilterChain(HttpSecurity http) throws Exception {
        CsrfTokenRequestAttributeHandler requestHandler = new CsrfTokenRequestAttributeHandler();
        requestHandler.setCsrfRequestAttributeName("_csrf");

        http.securityContext((context) -> context
                        .requireExplicitSave(false))
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.ALWAYS))
                .cors(corsCustomizer -> corsCustomizer.configurationSource(new CorsConfigurationSource() {
                    // Actual code to be used
                    @Override
                    public CorsConfiguration getCorsConfiguration(HttpServletRequest request) {
                        CorsConfiguration config = new CorsConfiguration();
                        config.setAllowedOrigins(Collections.singletonList("http://localhost:4200"));
                        config.setAllowedMethods(Collections.singletonList("*"));
                        config.setAllowCredentials(true);
                        config.setAllowedHeaders(Collections.singletonList("*"));
                        config.setMaxAge(3600L);
                        return config;
                    }
                })).csrf((csrf) -> csrf.csrfTokenRequestHandler(requestHandler).ignoringRequestMatchers("/contact", "/register","/confirmEmail/**","/UpdateAppointmentByLimitedInfo","/updateAppointment","/resetEmailRequest","/changePassword"   ,"/upload","/getFIles","/download/*"  ,"/myAccount" ,"/updateUserAccount"  ,"/patients" ,"/updatePatients")
                        .csrfTokenRepository(CookieCsrfTokenRepository.withHttpOnlyFalse()))
                .addFilterAfter(new CsrfCookieFilter(), BasicAuthenticationFilter.class)
                .authorizeHttpRequests((requests)->requests
                                .requestMatchers(
                                         "/contact", "/register",
                                        "/user", "/notices",  "/confirmEmail/**"
                                        ,"/resetEmailRequest","/changePassword"
                                        ,"/upload","/getFIles","/download/*"
                                )
                                .permitAll()
                                .requestMatchers(  "/users/{uuid}","/myAccount","/updateUserAccount","/PatientAppointment",
                                        "/AppointmentByCriteria","/AppointmentById","/myPatientPrescription"
                                        )
                                .authenticated()
                        .requestMatchers( "/AppointmentById", "/contact", "/register",
                                "/user", "/notices", "/users/{uuid}", "/confirmEmail/**")
                        .hasAnyRole("USER", "ADMIN","DOCTOR", "PATIENT")
                        .requestMatchers(
                                "/PrescriptionById","/myDrugs"
                                )
                        .hasAnyRole("DOCTOR", "PHARMACIST")
                        .requestMatchers("/SearchForPrescription", "/updatePrescription","myAvailableDrugs")
                        .hasAnyRole( "PHARMACIST")
                        .requestMatchers("/PatientAppointment", "/AppointmentById",
                                "/myPatientPrescription","/AppointmentByCriteria",
                                "/UpdateAppointmentByLimitedInfo","/updateAppointment")
                        .hasAnyRole("PATIENT")
                        .requestMatchers("/UpdateAppointment", "/DoctorAppointment",
                                "/DoctorsSchedule", "/UpdateSchedule", "/CreateNewSchedules",
                                "/myDoctorPrescription",  "/Schedule","/updatePatients","/patients","/createPrescription"
                                )
                        .hasAnyRole("DOCTOR")
                )
                .formLogin(Customizer.withDefaults())
                .httpBasic(Customizer.withDefaults());
        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

}
