package com.proje.healpoint.handler;

import com.proje.healpoint.exception.BaseException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(value = {BaseException.class})
    public ResponseEntity<String> handleBaseException(BaseException exception) {
        return ResponseEntity.badRequest().body(exception.getMessage());
    }
}
