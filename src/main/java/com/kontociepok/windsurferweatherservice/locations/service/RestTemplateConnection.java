package com.kontociepok.windsurferweatherservice.locations.service;

import com.kontociepok.windsurferweatherservice.locations.controller.LocationDto;
import com.kontociepok.windsurferweatherservice.locations.exception.WeatherServiceException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;


public class RestTemplateConnection {

     ResponseEntity<LocationDto> result;

     RestTemplateConnection(String url) {

         RestTemplate restTemplate = new RestTemplate();
         try {
             result = restTemplate.getForEntity(url, LocationDto.class);
         } catch (Exception e) {
             throw new WeatherServiceException("Something went wrong! Failed connection to WeatherBit");
             }
         }

    LocationDto response(){
        LocationDto locationDto;
        try{
            locationDto = result.getBody();
        }catch (Exception e){
            throw new WeatherServiceException(e);
        }
        return locationDto;
    }
}
