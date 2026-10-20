package com.example.todo.auth.service;

import com.example.todo.notification.event.EmailNotificationEvent;
import com.example.todo.notification.producer.NotificationEventProducer;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import java.security.SecureRandom;
import java.time.Duration;

@Service
public class OtpService {
    private final StringRedisTemplate redis;
    private final NotificationEventProducer events;
    private final SecureRandom random = new SecureRandom();

    public OtpService(StringRedisTemplate redis, NotificationEventProducer events) {
        this.redis = redis;
        this.events = events;
    }

    public void request(String email) {
        String normalizedEmail = email.trim().toLowerCase();
        String code = "%06d".formatted(random.nextInt(1_000_000));
        redis.opsForValue().set(key(normalizedEmail), code, Duration.ofMinutes(5));
        events.publish(EmailNotificationEvent.otpRequested(normalizedEmail, code));
    }

    public void verify(String email, String code) {
        String normalizedEmail = email.trim().toLowerCase();
        String stored = redis.opsForValue().getAndDelete(key(normalizedEmail));
        if (stored == null || !stored.equals(code))
            throw new IllegalArgumentException("Invalid or expired verification code");
    }

    private String key(String email) {
        return "otp:email:" + email;
    }
}
