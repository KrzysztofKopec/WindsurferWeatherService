package com.kontociepok.windsurferweatherservice.locations.controller;

public class LocationCoordinatesResponse {

    private final Long id;
    private final Double lat;
    private final Double lon;

    public LocationCoordinatesResponse(Long id, Double lat, Double lon) {
        this.id = id;
        this.lat = lat;
        this.lon = lon;
    }

    public Long getId() {
        return id;
    }

    public Double getLat() {
        return lat;
    }

    public Double getLon() {
        return lon;
    }
}
