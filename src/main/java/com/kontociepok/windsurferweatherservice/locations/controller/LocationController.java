package com.kontociepok.windsurferweatherservice.locations.controller;

import com.kontociepok.windsurferweatherservice.locations.model.LocationCoordinates;
import com.kontociepok.windsurferweatherservice.locations.service.LocationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class LocationController {

    private final LocationService locationService;

    @Autowired
    public LocationController(LocationService locationService) {
        this.locationService = locationService;
    }

    @GetMapping("/date")
    ResponseEntity<List<LocationResponse>> listBestPlace(@RequestParam String date){
        return new ResponseEntity<>(locationService.allBestPlace(date), HttpStatus.OK);
    }

    @PostMapping("/coordinates")
    LocationCoordinatesResponse addCoordinate(@RequestParam Double lat, @RequestParam Double lon){
        return locationService.addCoordinate(lat, lon);
    }
    @GetMapping("/coordinates")
    List<LocationCoordinatesResponse> allCoordinates(){
        return locationService.allCoordinates();
    }

    @DeleteMapping("/coordinates")
    LocationCoordinatesResponse deleteCoordinates(@RequestParam long coordinatesId){
        return locationService.deleteCoordinates(coordinatesId);
    }
}
