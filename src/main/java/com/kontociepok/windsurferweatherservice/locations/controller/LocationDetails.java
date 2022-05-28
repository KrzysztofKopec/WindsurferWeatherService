package com.kontociepok.windsurferweatherservice.locations.controller;

public class LocationDetails {

    private String datetime;
    private Double temp;
    private Double wind_spd;

    public LocationDetails(String datetime, Double temp, Double wind_spd) {
        this.datetime = datetime;
        this.temp = temp;
        this.wind_spd = wind_spd;
    }

    public LocationDetails() {
    }

    public String getDatetime() {
        return datetime;
    }

    public void setDatetime(String datetime) {
        this.datetime = datetime;
    }

    public void setTemp(Double temp) {
        this.temp = temp;
    }

    public void setWind_spd(Double wind_spd) {
        this.wind_spd = wind_spd;
    }

    public Double getTemp() {
        return temp;
    }

    public Double getWind_spd() {
        return wind_spd;
    }

}
