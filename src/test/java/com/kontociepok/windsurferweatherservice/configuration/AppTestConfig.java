package com.kontociepok.windsurferweatherservice.configuration;

import com.kontociepok.windsurferweatherservice.locations.controller.WeatherBitClientStub;
import com.kontociepok.windsurferweatherservice.locations.service.Weather;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

@Profile("test")
@Configuration
public class AppTestConfig{

    @Bean
    public Weather connection() {
        return new WeatherBitClientStub();
    }
}
