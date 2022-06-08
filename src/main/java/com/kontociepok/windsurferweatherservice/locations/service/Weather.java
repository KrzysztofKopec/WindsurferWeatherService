package com.kontociepok.windsurferweatherservice.locations.service;

import com.kontociepok.windsurferweatherservice.locations.controller.LocationDto;

public interface Weather {

    LocationDto getWeather(String date);

}
