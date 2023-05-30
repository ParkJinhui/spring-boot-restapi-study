package com.example.common.exception;


import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class CommonAdvice {
    @ExceptionHandler(Common400Exception.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    CommonError on400Exception(Common400Exception exception) {
        return new CommonError(exception.getMessage());
    }

}
