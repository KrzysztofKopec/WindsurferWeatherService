package com.kontociepok.windsurferweatherservice.locations.model;

import javax.persistence.*;

@Entity
@Table(name = "locations")
public class LocationCoordinates {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private double lat;
    private double lon;


    public LocationCoordinates(double lat, double lon) {
        this.lat = lat;
        this.lon = lon;
    }

    public LocationCoordinates() {
    }

    public Long getId() {
        return id;
    }

    public double getLat() {
        return lat;
    }

    public void setLat(double lat) {
        this.lat = lat;
    }

    public double getLon() {
        return lon;
    }

    public void setLon(double lon) {
        this.lon = lon;
    }

}
