package com.github.register_service.request;

import lombok.Data;

@Data
public class UserResponseDTO {
    private String username;
    private String password;
    private String role;
}
