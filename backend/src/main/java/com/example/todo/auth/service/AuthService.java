package com.example.todo.auth.service;

import com.example.todo.auth.dto.*;
import com.example.todo.auth.entity.User;
import com.example.todo.auth.repository.UserRepository;
import com.example.todo.common.exception.ResourceNotFoundException;
import com.example.todo.notification.event.EmailNotificationEvent;
import com.example.todo.notification.producer.NotificationEventProducer;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthService {
    private final UserRepository users;
    private final PasswordEncoder encoder;
    private final AuthenticationManager authenticationManager;
    private final JwtService jwt;
    private final RefreshTokenService refreshTokens;
    private final NotificationEventProducer events;

    public AuthService(UserRepository users, PasswordEncoder encoder, AuthenticationManager authenticationManager,
            JwtService jwt, RefreshTokenService refreshTokens, NotificationEventProducer events) {
        this.users = users;
        this.encoder = encoder;
        this.authenticationManager = authenticationManager;
        this.jwt = jwt;
        this.refreshTokens = refreshTokens;
        this.events = events;
    }

    public AuthResponse register(RegisterRequest request) {
        String email = request.email().trim().toLowerCase();
        if (users.existsByEmailIgnoreCase(email))
            throw new IllegalArgumentException("Email is already registered");
        User user = users.save(new User(email, encoder.encode(request.password())));
        return tokensFor(user);
    }

    public AuthResponse login(LoginRequest request) {
        String email = request.email().trim().toLowerCase();
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(email, request.password()));
        User user = users.findByEmailIgnoreCase(email)
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));
        AuthResponse response = tokensFor(user);
        events.publish(EmailNotificationEvent.loginSucceeded(user.getId(), user.getEmail()));
        return response;
    }

    public AuthResponse refresh(RefreshTokenRequest request) {
        User user = refreshTokens.validateAndRotate(request.refreshToken());
        return tokensFor(user);
    }

    public void logout(RefreshTokenRequest request) {
        refreshTokens.revoke(request.refreshToken());
    }

    private AuthResponse tokensFor(User user) {
        return new AuthResponse(jwt.createAccessToken(user.getId(), user.getEmail()), refreshTokens.create(user),
                jwt.getExpirationSeconds());
    }
}
