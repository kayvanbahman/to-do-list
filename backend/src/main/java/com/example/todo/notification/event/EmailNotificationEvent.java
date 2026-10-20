package com.example.todo.notification.event;

import java.time.Instant;
import java.util.UUID;

public record EmailNotificationEvent(String eventId, String type, Long userId, String email, String otpCode,
        Instant createdAt) {
    public static EmailNotificationEvent loginSucceeded(Long userId, String email) {
        return new EmailNotificationEvent(UUID.randomUUID().toString(), "LOGIN_SUCCEEDED", userId, email, null,
                Instant.now());
    }

    public static EmailNotificationEvent otpRequested(String email, String otpCode) {
        return new EmailNotificationEvent(UUID.randomUUID().toString(), "OTP_REQUESTED", null, email, otpCode,
                Instant.now());
    }
}
