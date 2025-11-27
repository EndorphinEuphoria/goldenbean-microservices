package com.github.register_service.service;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.transaction.annotation.Transactional;

import com.github.register_service.model.Rol;
import com.github.register_service.model.User;
import com.github.register_service.repository.RolRepository;
import com.github.register_service.repository.UserRepository;
import com.github.register_service.request.AuthResponse;
import com.github.register_service.request.LoginRequest;
import com.github.register_service.request.RegisterRequest;
import com.github.register_service.request.RegisterResponse;

import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;

@Service
@Transactional
@RequiredArgsConstructor
public class UserService {
    
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final RolRepository rolRepository;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;

    public AuthResponse login (LoginRequest request){
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword()));
        UserDetails user = userRepository.findByUsername(request.getUsername())
                .orElseThrow(() -> new RuntimeException("User not found"));
        String token = jwtService.getToken(user);
        return AuthResponse.builder()
                .token(token)
                .build();
    }


   public RegisterResponse registerUser(RegisterRequest request) {

    Rol rol = rolRepository.findById(2L)
            .orElseThrow(() -> new RuntimeException("Rol no encontrado"));

    User user = User.builder()
            .email(request.getEmail())
            .username(request.getUsername())
            .password(passwordEncoder.encode(request.getPassword()))
            .rol(rol)
            .build();

    userRepository.save(user);

    return RegisterResponse.builder()
            .message("Usuario registrado correctamente")
            .status(201).count(1)
            .build();
}


    public void deleteUserById(Long userId){
        userRepository.deleteById(userId);
    }

    

}
