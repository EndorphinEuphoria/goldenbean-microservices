package com.github.coffee_service.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ErrorResponseDto {
    private String message;
    private int status;
    private long timestamp;
    private String path;
}
