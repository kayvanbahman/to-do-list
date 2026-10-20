package com.example.todo.notification.producer;

import com.example.todo.notification.event.EmailNotificationEvent;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Component
public class NotificationEventProducer {
    private final KafkaTemplate<String, EmailNotificationEvent> kafka;
    private final String topic;

    public NotificationEventProducer(KafkaTemplate<String, EmailNotificationEvent> kafka,
            @Value("${app.kafka.email-topic}") String topic) {
        this.kafka = kafka;
        this.topic = topic;
    }

    public void publish(EmailNotificationEvent event) {
        kafka.send(topic, event.email(), event);
    }
}
