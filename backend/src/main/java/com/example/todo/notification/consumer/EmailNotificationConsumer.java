package com.example.todo.notification.consumer;

import com.example.todo.notification.event.EmailNotificationEvent;
import com.example.todo.notification.service.EmailService;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
public class EmailNotificationConsumer {
    private final EmailService emailService;

    public EmailNotificationConsumer(EmailService emailService) {
        this.emailService = emailService;
    }

    @KafkaListener(topics = "${app.kafka.email-topic}", groupId = "email-notification-service")
    public void consume(EmailNotificationEvent event) {
        emailService.sendLoginNotification(event);
    }
}
