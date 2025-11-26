package com.github.register_service.controller;


import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.github.register_service.request.AuthResponse;
import com.github.register_service.request.LoginRequest;
import com.github.register_service.request.RegisterRequest;
import com.github.register_service.service.UserService;

@RestController
@RequestMapping("/api-v1/register")
@RequiredArgsConstructor
public class RegisterController {

    private final UserService userService;

    @PostMapping
    public ResponseEntity<String> register(@RequestBody RegisterRequest request) {
        userService.registerUser(request);
        return ResponseEntity.ok("Usuario registrado correctamente");
    }

      @PostMapping("/login")
    public ResponseEntity<AuthResponse> login(@RequestBody LoginRequest request) {
        AuthResponse response = userService.login(request);
        return ResponseEntity.ok(response);
    }
}
