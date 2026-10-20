package com.example.todo.auth.dto;

public record AuthResponse(String accessToken, String refreshToken, long expiresInSeconds) {
}
