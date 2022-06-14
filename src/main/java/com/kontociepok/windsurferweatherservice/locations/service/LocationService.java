package com.kontociepok.windsurferweatherservice.locations.service;

import com.kontociepok.windsurferweatherservice.locations.controller.*;
import com.kontociepok.windsurferweatherservice.locations.exception.CustomBadFormatDate;
import com.kontociepok.windsurferweatherservice.locations.exception.CustomParameterConstraintException;
import com.kontociepok.windsurferweatherservice.locations.model.LocationCoordinates;
import com.kontociepok.windsurferweatherservice.locations.repository.LocationCoordinatesRepo;
import org.springframework.stereotype.Service;

import java.time.DateTimeException;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class LocationService {

    private final LocationCoordinatesRepo locationCoordinatesRepo;
    private final Weather weather;
    private final BestWeatherSelector bestWeatherSelector;

    public LocationService(LocationCoordinatesRepo locationCoordinatesRepo, Weather weather, BestWeatherSelector bestWeatherSelector) {
        this.locationCoordinatesRepo = locationCoordinatesRepo;
        this.weather = weather;
        this.bestWeatherSelector = bestWeatherSelector;
    }


    public int getDaysBetweenDateAndNow(String date){
        LocalDate dateToday = LocalDate.now();
        try {
            LocalDate dateSearch = LocalDate.parse(date);
            return (int) ChronoUnit.DAYS.between(dateToday, dateSearch);
        }catch (DateTimeException e){
            throw new CustomBadFormatDate("Bad format date: "+ e.getMessage());
        }
    }

    public List<LocationResponse> allBestPlace(String date){
        int days = getDaysBetweenDateAndNow(date);

        if(days < 0 || days > 15) throw new CustomParameterConstraintException("Date is too old or too far away");

        List<LocationDto> locations = getWeathers();

        return bestWeatherSelector.selectBestWeather(locations, days);
    }

    public List<LocationDto> getWeathers(){
        List<LocationDto> locations = new ArrayList<>();
        List<LocationCoordinates> locationCoordinates = locationCoordinatesRepo.findAll();
        List<LocationResponse> locationsResponse = new ArrayList<>();

        String coordinates;
        for(LocationCoordinates x: locationCoordinates){
            coordinates = "lat=" +x.getLat()+"&lon="+x.getLon();

            LocationDto location = weather.getWeather(coordinates);
            locations.add(location);
        }
        return locations;
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

    public LocationCoordinatesResponse deleteCoordinates(long coordinatesId) {
        LocationCoordinates coordinates = locationCoordinatesRepo.getById(coordinatesId);
        locationCoordinatesRepo.deleteById(coordinatesId);
        return convertToLocationCoordinatesResponse(coordinates);
    }

}
