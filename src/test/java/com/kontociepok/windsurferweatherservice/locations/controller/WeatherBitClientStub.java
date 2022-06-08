package com.kontociepok.windsurferweatherservice.locations.controller;

import com.kontociepok.windsurferweatherservice.locations.service.Weather;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

@Service
public class WeatherBitClientStub implements Weather {

    @Override
    public LocationDto getWeather(String coordinates) {
        String date = LocalDate.now().toString();
        LocationDetails locationDetails = new LocationDetails(date,10.10,11.11);
        LocationDetails[] details = {locationDetails};
        return new LocationDto("Cracow",details);
    }
}
