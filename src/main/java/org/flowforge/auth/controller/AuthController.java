package org.flowforge.auth.controller;

import lombok.RequiredArgsConstructor;

import org.flowforge.auth.dto.LoginRequest;
import org.flowforge.auth.dto.LoginResponse;
import org.flowforge.auth.dto.RegisterRequest;

import org.flowforge.auth.entity.User;

import org.flowforge.auth.service.AuthService;

import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @PostMapping("/register")
    public User register(
            @RequestBody RegisterRequest request
    ) {
        return authService.register(request);
    }

    @PostMapping("/login")
    public LoginResponse login(
            @RequestBody LoginRequest request
    ) {
        return authService.login(request);
    }
}