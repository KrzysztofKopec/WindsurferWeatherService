package com.kontociepok.windsurferweatherservice.locations.service;

import com.kontociepok.windsurferweatherservice.locations.controller.LocationDto;
import com.kontociepok.windsurferweatherservice.locations.exception.WeatherServiceException;
import org.springframework.beans.factory.annotation.Autowired;
import io.github.resilience4j.circuitbreaker.CircuitBreaker;
import org.springframework.stereotype.Service;

import java.util.function.Supplier;

@Service
class WeatherBitClient implements Weather {

    @Autowired
    private CircuitBreaker countCircuitBreaker;

    @Override
    public LocationDto getWeather(String coordinates) {

        try {

            String url = "https://api.weatherbit.io/v2.0/forecast/daily?" + coordinates + "&key=160bf06dc0924e2e9861636c78213988";
            Supplier<LocationDto> locationDtoSupplier = countCircuitBreaker.decorateSupplier(() -> new RestTemplateConnection(url).response());
            return locationDtoSupplier.get();

        } catch (Exception e) {

            throw new WeatherServiceException(String.format("Something went wrong! %s", e));

        }
    }
}
