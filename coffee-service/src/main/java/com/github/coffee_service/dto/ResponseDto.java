package com.github.coffee_service.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ResponseDto<T> {
    private String message;
    private Integer status;
    private T data;
    private Integer count;

    public ResponseDto(String message, Integer status, Integer count) {
        this.message = message;
        this.status = status;
        this.count = count;
    }
}