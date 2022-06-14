package com.kontociepok.windsurferweatherservice.locations.repository;

import com.kontociepok.windsurferweatherservice.locations.model.LocationCoordinates;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;


import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(SpringExtension.class)
@DataJpaTest
@AutoConfigureTestDatabase()
public class LocationsCoordinatesRepoTest {

    @Autowired
    LocationCoordinatesRepo locationCoordinatesRepo;

    @Test
    void shouldReturnAllCoordinatesWhenExists(){
        //given
        locationCoordinatesRepo.deleteAll();
        var coordinates = new LocationCoordinates(12.34,56.78);
        locationCoordinatesRepo.save(coordinates);

        //when
        var result = locationCoordinatesRepo.findAll();

        //then
        assertThat(result.size()).isEqualTo(1);
        assertThat(result.contains(coordinates)).isTrue();
    }

    @Test
    void shouldReturnCoordinatesByIdWhenExists(){
        //given
        locationCoordinatesRepo.deleteAll();
        var coordinates = new LocationCoordinates(12.34,56.78);
        locationCoordinatesRepo.save(coordinates);

        //when
        var result = locationCoordinatesRepo.findById(9L);

        //then
        assertThat(result.isPresent()).isEqualTo(result.get().getLat() == 12.34);
    }

    @Test
    void shouldSaveCoordinates(){
        //given
        locationCoordinatesRepo.deleteAll();
        var coordinates = new LocationCoordinates(12.34,56.78);

        //when
        locationCoordinatesRepo.save(coordinates);

        //then
        var result = locationCoordinatesRepo.findAll();
        assertThat(result.size()).isEqualTo(1);
        assertThat(result.contains(coordinates)).isTrue();

    }

    @Test
    void shouldDeletecoordinatesByIdWhenExists(){
        //given
        locationCoordinatesRepo.deleteAll();
        var coordinates = new LocationCoordinates(12.34,56.78);
        var coordinates1 = new LocationCoordinates(33.11,44.22);
        locationCoordinatesRepo.save(coordinates);
        locationCoordinatesRepo.save(coordinates1);

        //when
        locationCoordinatesRepo.deleteById(6L);

        //then
        assertThat(locationCoordinatesRepo.findAll().size()).isEqualTo(1);

    }
}
