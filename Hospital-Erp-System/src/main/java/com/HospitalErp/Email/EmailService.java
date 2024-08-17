package com.HospitalErp.Email;

import jakarta.mail.internet.MimeMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;
import javax.mail.internet.InternetAddress;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.UUID;


@Service
public class EmailService {

    @Autowired
    private JavaMailSender emailSender;

    @Autowired
    private TemplateEngine templateEngine;

    public void sendSimpleEmail(String to, String subject, String text) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(to);
        message.setSubject(subject);
        message.setText(text);
        emailSender.send(message);
    }

    public void sendRegistrationConfirmation(String email, String username, UUID uuid) throws Exception {
        // Create the Thymeleaf context
        Context context = new Context();
        context.setVariable("username", username);
        context.setVariable("uuid", uuid);
        context.setVariable("email", email);
        context.setVariable("Link", generateLink(email, uuid));
        System.out.println("the token is " + uuid.toString());

        // Process the email template
        String emailContent = templateEngine.process("Registration-Confirmation-Email.html", context);

        // Create a MimeMessage
        MimeMessage mimeMessage = emailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true);

        // Set the subject, content, and recipient
        helper.setSubject("Registration Confirmation");
        helper.setText(emailContent, true); // true indicates that this is HTML content
        helper.setTo(email); // Set the email address directly

        // Send the email
        emailSender.send(mimeMessage);
    }

    public void sendResetEmail(String email,UUID token,String username) throws Exception {
        // Create the Thymeleaf context
        Context context = new Context();
        context.setVariable("email", email);
        context.setVariable("username", username);
        context.setVariable("uuid", token);
        context.setVariable("email", email);
        context.setVariable("Link", "http://localhost:4200/changeMyPassword?"+"uuid="+token.toString());


        // Process the email template
        String emailContent = templateEngine.process("Reset-Password-Email.html", context);

        // Create a MimeMessage
        MimeMessage mimeMessage = emailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true);

        // Set the subject, content, and recipient
        helper.setSubject("Forgot Email");
        helper.setText(emailContent, true); // true indicates that this is HTML content
        helper.setTo(email); // Set the email address directly

        // Send the email
        emailSender.send(mimeMessage);
    }

    public String generateLink(String email, UUID uuid) throws UnsupportedEncodingException {
        return "http://localhost:8080/confirmEmail?"+"uuid="+uuid.toString();
    }

}