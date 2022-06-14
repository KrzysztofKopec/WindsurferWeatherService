package com.kontociepok.windsurferweatherservice.locations.controller;

import com.kontociepok.windsurferweatherservice.locations.controller.LocationDetails;
import com.kontociepok.windsurferweatherservice.locations.controller.LocationDto;
import com.kontociepok.windsurferweatherservice.locations.service.Weather;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

@Component
@Profile("test")
public class WeatherBitClientStub implements Weather {

    @Override
    public LocationDto getWeather(String coordinates) {
        String date = LocalDate.now().toString();
        LocationDetails locationDetails = new LocationDetails(date,10.10,11.11);
        LocationDetails[] details = {locationDetails};
        return new LocationDto("Cracow",details);
    }
}
