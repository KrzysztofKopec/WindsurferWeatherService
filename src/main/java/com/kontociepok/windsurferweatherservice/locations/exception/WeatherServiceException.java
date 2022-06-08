package com.kontociepok.windsurferweatherservice.locations.exception;

public class WeatherServiceException extends RuntimeException{

        public WeatherServiceException(String message) {
            super(message);
        }

        public WeatherServiceException(Throwable cause) { super(cause); }
}
