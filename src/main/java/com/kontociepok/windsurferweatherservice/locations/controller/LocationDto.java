package com.kontociepok.windsurferweatherservice.locations.controller;

public class LocationDto {

    private String city_name;
    private LocationDetails[] data;

    public LocationDto(String city_name, LocationDetails[] data) {
        this.city_name = city_name;
        this.data = data;
    }

    public LocationDto() {
    }

    public void setCity_name(String city_name) {
        this.city_name = city_name;
    }

    public void setData(LocationDetails[] data) {
        this.data = data;
    }

    public String getCity_name() {
        return city_name;
    }

    public LocationDetails[] getData() {
        return data;
    }
}
