package com.github.login_service.DTO;
import lombok.Data;

@Data
public class ExternalUserDTO {
    private String username;
    private String password;
    private String role;
}
