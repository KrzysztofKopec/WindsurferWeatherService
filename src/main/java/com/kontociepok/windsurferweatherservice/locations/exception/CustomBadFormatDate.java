package com.kontociepok.windsurferweatherservice.locations.exception;

import java.time.DateTimeException;

public class CustomBadFormatDate extends DateTimeException {


    public CustomBadFormatDate(String message) {
        super(message);
    }
}

