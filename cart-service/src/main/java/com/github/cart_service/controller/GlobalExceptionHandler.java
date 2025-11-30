package com.github.cart_service.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.github.cart_service.dto.ErrorResponseDto;
import com.github.cart_service.exception.CartNotFoundException;

import jakarta.servlet.http.HttpServletRequest;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(CartNotFoundException.class)
    public ResponseEntity<ErrorResponseDto> handleCartNotFound(
        CartNotFoundException ex,
        HttpServletRequest request
    ) {
        ErrorResponseDto error = new ErrorResponseDto(
            ex.getMessage(),
            HttpStatus.NOT_FOUND.value(),
            System.currentTimeMillis(),
            request.getRequestURI()
        );

        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(error);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponseDto> handleGeneralCartError(
        Exception ex,
        HttpServletRequest request
    ) {
        ErrorResponseDto error = new ErrorResponseDto(
            ex.getMessage(),
            HttpStatus.BAD_REQUEST.value(),
            System.currentTimeMillis(),
            request.getRequestURI()
        );

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);
    }
}
