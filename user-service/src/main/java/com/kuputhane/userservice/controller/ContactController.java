package com.kuputhane.userservice.controller;

import com.kuputhane.userservice.dto.ContactRequest;
import jakarta.mail.Message;
import jakarta.mail.MessagingException;
import jakarta.mail.PasswordAuthentication;
import jakarta.mail.Session;
import jakarta.mail.Transport;
import jakarta.mail.internet.InternetAddress;
import jakarta.mail.internet.MimeMessage;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Properties;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/api/contact")
public class ContactController {

    @PostMapping
    public ResponseEntity<String> sendMail(@RequestBody ContactRequest request) {
        String subject = "Yeni İletişim Mesajı Geldi!";
        String body = "Ad: " + request.getName() + "\n" +
                "Soyad: " + request.getSurname() + "\n" +
                "Telefon: " + request.getPhone() + "\n" +
                "Mesaj: " + request.getMessage();

        sendEmail(subject, body);
        return ResponseEntity.ok("Mesaj başarıyla gönderildi!");
    }

    private void sendEmail(String subject, String body) {
        final String username = "duygua963@gmail.com";
        final String password = "arvbxtutayuoxedy";

        Properties props = new Properties();
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.host", "smtp.gmail.com");
        props.put("mail.smtp.port", "587");

        Session session = Session.getInstance(props,
                new jakarta.mail.Authenticator() {
                    @Override
                    protected PasswordAuthentication getPasswordAuthentication() {
                        return new PasswordAuthentication(username, password);
                    }
                });

        try {
            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress(username));
            message.setRecipients(
                    Message.RecipientType.TO,
                    InternetAddress.parse("duygua963@gmail.com")
            );
            message.setSubject(subject);
            message.setText(body);

            Transport.send(message);

        } catch (MessagingException e) {
            e.printStackTrace();
            throw new RuntimeException("Mail gönderilemedi: " + e.getMessage());
        }
    }
}
