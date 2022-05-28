package com.kontociepok.windsurferweatherservice.locations.controller;

public class LocationResponse {

    private final String day;
    private final String name;
    private final double temp;
    private final double wind;
    private final Double totalScore;

    public LocationResponse(String day, String name, double temp, double wind) {
        this.day = day;
        this.name = name;
        this.temp = temp;
        this.wind = wind;
        totalScore = wind * 3 + temp;
    }

    public String getDay() {
        return day;
    }

    public String getName() {
        return name;
    }

    public double getTemp() {
        return temp;
    }

    public double getWind() {
        return wind;
    }

    public Double getTotalScore() {
        return totalScore;
    }
}
