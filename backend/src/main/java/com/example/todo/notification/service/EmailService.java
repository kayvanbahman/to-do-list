package com.example.todo.notification.service;

import com.example.todo.notification.event.EmailNotificationEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class EmailService {
    private static final Logger log = LoggerFactory.getLogger(EmailService.class);
    private final JavaMailSender mailSender;

    public EmailService(JavaMailSender mailSender) {
        this.mailSender = mailSender;
    }

    public void sendLoginNotification(EmailNotificationEvent event) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(event.email());
        if ("OTP_REQUESTED".equals(event.type())) {
            message.setSubject("Your Todo verification code");
            message.setText("Your verification code is " + event.otpCode() + ". It expires in five minutes.");
        } else {
            message.setSubject("New login to your Todo account");
            message.setText("A new login was detected on your Todo account at " + event.createdAt() + ".");
        }
        try {
            mailSender.send(message);
        } catch (Exception ex) {
            log.error("Could not send login email for event {}", event.eventId(), ex);
            throw new IllegalStateException("Email delivery failed", ex);
        }
    }
}
