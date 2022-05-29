package com.kontociepok.windsurferweatherservice.locations.service;

import com.kontociepok.windsurferweatherservice.locations.controller.LocationCoordinatesResponse;
import com.kontociepok.windsurferweatherservice.locations.controller.LocationDetails;
import com.kontociepok.windsurferweatherservice.locations.model.LocationCoordinates;
import com.kontociepok.windsurferweatherservice.locations.controller.LocationDto;
import com.kontociepok.windsurferweatherservice.locations.controller.LocationResponse;
import com.kontociepok.windsurferweatherservice.locations.repository.LocationCoordinatesRepo;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class LocationService {

    private final LocationCoordinatesRepo locationCoordinatesRepo;

    public LocationService(LocationCoordinatesRepo locationCoordinatesRepo) {
        this.locationCoordinatesRepo = locationCoordinatesRepo;
    }

    public int checkDate(String date){
        LocalDate dateToday = LocalDate.now();
        LocalDate dateSearch = LocalDate.parse(date);
        return (int)ChronoUnit.DAYS.between(dateToday, dateSearch);
//        if(dateToday.isEqual(dateSearch) || dateToday.isBefore(dateSearch)){
//            return (int)ChronoUnit.DAYS.between(dateToday, dateSearch);
//        }
//        return -1;
    }

    public List<LocationResponse> allBestPlace(String date) {
        if(checkDate(date) < 0 || checkDate(date) > 15) return null;
        return checkBestPlace(checkDate(date))
                .stream()
                .sorted(Comparator.comparing(LocationResponse::getTotalScore).reversed())
                .collect(Collectors.toList());
    }
    
    public List<LocationResponse> checkBestPlace(int days){
        RestTemplate restTemplate = new RestTemplate();
        List<LocationCoordinates> locationCoordinates = locationCoordinatesRepo.findAll();
        List<LocationResponse> locationsResponse = new ArrayList<>();
        LocationDetails locationDetails;
        String coordinates;
        for(LocationCoordinates x: locationCoordinates){
            coordinates = "lat=" +x.getLat()+"&lon="+x.getLon();
            LocationDto location = restTemplate.getForObject(
                    "https://api.weatherbit.io/v2.0/forecast/daily?"+coordinates+"&key=160bf06dc0924e2e9861636c78213988",
                    LocationDto.class
            );
            locationDetails = location.getData()[days];
            if((locationDetails.getWind_spd()<5 || locationDetails.getWind_spd()>18) ||
                    (locationDetails.getTemp()<5 || locationDetails.getTemp()>35))continue;

            locationsResponse.add(convertToLocationResponse(location, days));
        }
        return locationsResponse;
    }

    private LocationResponse convertToLocationResponse(LocationDto locationDto, int days){
        return new LocationResponse(locationDto.getData()[days].getDatetime(),locationDto.getCity_name(),
                locationDto.getData()[days].getTemp(),locationDto.getData()[days].getWind_spd());
    }

    private LocationCoordinatesResponse convertToLocationCoordinatesResponse(LocationCoordinates locationCoordinates){
        return new LocationCoordinatesResponse(locationCoordinates.getId(),locationCoordinates.getLat(),locationCoordinates.getLon());
    }

    public LocationCoordinatesResponse addCoordinate(Double lat, Double lon) {
        var coordinates = new LocationCoordinates(lat, lon);
        return convertToLocationCoordinatesResponse(locationCoordinatesRepo.save(coordinates));
    }

    public List<LocationCoordinatesResponse> allCoordinates() {
        return locationCoordinatesRepo.findAll().stream().map(this::convertToLocationCoordinatesResponse).collect(Collectors.toList());
    }
}
