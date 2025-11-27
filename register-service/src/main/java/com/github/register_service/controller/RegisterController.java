package com.github.register_service.controller;


import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.github.register_service.request.RegisterRequest;
import com.github.register_service.request.RegisterResponse;
import com.github.register_service.request.UserResponseDTO;
import com.github.register_service.service.UserService;


@RestController
@RequestMapping("/api-v1/register")
@RequiredArgsConstructor
public class RegisterController {

    private final UserService userService;

    @PostMapping
    public ResponseEntity<RegisterResponse> register(@RequestBody RegisterRequest request) {
        RegisterResponse response = userService.registerUser(request);
        return ResponseEntity.status(201).body(response); 
    }


     @GetMapping("/{username}")
    public UserResponseDTO getUserByUsername(@PathVariable String username) {
        return userService.getUserByUsername(username);
    }
    
    
}
