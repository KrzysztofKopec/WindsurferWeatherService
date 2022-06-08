package com.kontociepok.windsurferweatherservice.locations.service;

import com.kontociepok.windsurferweatherservice.locations.controller.LocationDetails;
import com.kontociepok.windsurferweatherservice.locations.controller.LocationDto;
import com.kontociepok.windsurferweatherservice.locations.controller.LocationResponse;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class BestWeatherSelector {

    public List<LocationResponse> selectBestWeather(List<LocationDto> locations, int days ){
        LocationDetails locationDetails;
        List<LocationResponse> locationsResponse = new ArrayList<>();
        for(LocationDto location: locations) {
            locationDetails = location.getData()[days];
            if ((locationDetails.getWind_spd() < 5 || locationDetails.getWind_spd() > 18) ||
                    (locationDetails.getTemp() < 5 || locationDetails.getTemp() > 35)) continue;
            locationsResponse.add(convertToLocationResponse(location, days));
        }
        return locationsResponse.stream()
                .sorted(Comparator.comparing(LocationResponse::getTotalScore).reversed())
                .collect(Collectors.toList());
    }

    private LocationResponse convertToLocationResponse(LocationDto locationDto, int days){
        return new LocationResponse(locationDto.getData()[days].getDatetime(),locationDto.getCity_name(),
                locationDto.getData()[days].getTemp(),locationDto.getData()[days].getWind_spd());
    }
}
