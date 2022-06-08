package com.kontociepok.windsurferweatherservice.locations.controller;

import java.util.Objects;

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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        LocationCoordinatesResponse that = (LocationCoordinatesResponse) o;
        return Objects.equals(lat, that.lat) &&
                Objects.equals(lon, that.lon);
    }

    @Override
    public int hashCode() {
        return Objects.hash(lat, lon);
    }
}
