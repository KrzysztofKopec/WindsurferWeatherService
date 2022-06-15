package com.kontociepok.windsurferweatherservice.locations.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;


@ControllerAdvice
public class CustomControllerAdvice {

    @ExceptionHandler(CustomBadFormatDate.class)
    public ResponseEntity<ErrorResponse> handlerBadFormatDate(Exception e){
        HttpStatus status = HttpStatus.BAD_REQUEST;
        return new ResponseEntity<>(new ErrorResponse(status, e.getMessage()), status);
    }
    @ExceptionHandler(CustomParameterConstraintException.class)
    public ResponseEntity<ErrorResponse> handlerCustomParameterConstraintExceptions(Exception e) {
        HttpStatus status = HttpStatus.BAD_REQUEST;
        return new ResponseEntity<>(new ErrorResponse(status, e.getMessage()), status);
    }
    @ExceptionHandler(WeatherServiceException.class)
    public ResponseEntity<ErrorResponse> handlerCustomConnectionExceptions(Exception e) {
        HttpStatus status = HttpStatus.INTERNAL_SERVER_ERROR;
        return new ResponseEntity<>(new ErrorResponse(status, e.getMessage()), status);
    }
}
