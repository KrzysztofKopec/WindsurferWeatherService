package com.kontociepok.windsurferweatherservice.locations.controller;

import com.kontociepok.windsurferweatherservice.locations.service.LocationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class LocationController {

    private final LocationService locationService;

    @Autowired
    public LocationController(LocationService locationService) {
        this.locationService = locationService;
    }

    @GetMapping("/date")
    List<LocationResponse> listBestPlace(@RequestParam String date){
        return locationService.allBestPlace(date);
    }
}
