package com.kontociepok.windsurferweatherservice.locations.service;

import com.kontociepok.windsurferweatherservice.locations.controller.LocationDto;
import com.kontociepok.windsurferweatherservice.locations.exception.WeatherServiceException;
import org.springframework.beans.factory.annotation.Autowired;
import io.github.resilience4j.circuitbreaker.CircuitBreaker;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.function.Supplier;

@Component
@Profile("prod")
class WeatherBitClient implements Weather {

    @Autowired
    private CircuitBreaker countCircuitBreaker;

    @Value("${firstStringUrl}")
    private String firstStringUrl;

    @Value("${secondStringUrl}")
    private String secondStringUrl;

    @Override
    public LocationDto getWeather(String coordinates) {

            String url = firstStringUrl + coordinates + secondStringUrl;
            Supplier<LocationDto> locationDtoSupplier = countCircuitBreaker.decorateSupplier(() -> new RestTemplateConnection(url).response());
            return locationDtoSupplier.get();

    }
}
