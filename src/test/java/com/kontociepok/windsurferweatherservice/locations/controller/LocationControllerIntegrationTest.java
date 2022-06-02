package com.kontociepok.windsurferweatherservice.locations.controller;

import com.kontociepok.windsurferweatherservice.locations.model.LocationCoordinates;
import com.kontociepok.windsurferweatherservice.locations.repository.LocationCoordinatesRepo;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;

import java.time.LocalDate;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class LocationControllerIntegrationTest {

    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    @Autowired
    private LocationCoordinatesRepo locationCoordinatesRepo;


    @Test
    void shouldReturnAllCoordinates(){

            // given
            locationCoordinatesRepo.deleteAll();
            locationCoordinatesRepo.save(new LocationCoordinates(11.22, 33.44));
            locationCoordinatesRepo.save(new LocationCoordinates(55.66,77.88));
            var expected = new LocationCoordinatesResponse(7L, 55.66,77.88);

            // when
            var result = restTemplate.getForEntity("http://localhost:" + port + "/coordinates",
                    LocationCoordinatesResponse[].class);

            // then
            assertThat(result.getStatusCodeValue() == 200);
            assertThat(result.hasBody()).isTrue();
            assertThat(result.getBody()).contains(expected);

    }
    @Test
    void shouldAddCoordinates(){
        //given
        locationCoordinatesRepo.deleteAll();
        var expected = new LocationCoordinatesResponse(10L,11.11,22.22);

        //when
        var result = restTemplate.postForEntity("http://localhost:" + port + "/coordinates?lat=11.11&lon=22.22",
                 LocationCoordinates.class,LocationCoordinatesResponse.class);

        //then
        assertThat(result.getStatusCodeValue() == 200);
        assertThat(result.hasBody()).isTrue();
        assertThat(result.getBody()).isEqualTo(expected);
    }
    @Test
    void shouldDeleteCoordinatesById(){
        //given
        locationCoordinatesRepo.deleteAll();
        locationCoordinatesRepo.save(new LocationCoordinates(11.22, 33.44));
        locationCoordinatesRepo.save(new LocationCoordinates(55.66,77.88));

        //when
        restTemplate.delete("http://localhost:" + port + "/coordinates?coordinatesId=11",
                LocationCoordinatesResponse.class);

        //then
        var result = restTemplate.getForEntity("http://localhost:" + port + "/coordinates",
                LocationCoordinatesResponse[].class);
        assertThat(result.getStatusCodeValue() == 200);
        assertThat(result.hasBody()).isTrue();
        assertThat(result.getBody()).hasSize(1);

    }

    @Test
    void shouldReturnListBestPlaceWhenExists(){
        //given
        locationCoordinatesRepo.deleteAll();
        locationCoordinatesRepo.save(new LocationCoordinates(54.7028, 18.6707));
        locationCoordinatesRepo.save(new LocationCoordinates(13.0968, -59.6144));
        String dateToday = LocalDate.now().toString();

        //when
        var result = restTemplate.getForEntity("http://localhost:" + port + "/date?date="+dateToday,
                LocationResponse[].class);

        //then
        assertThat(result.getStatusCodeValue() == 200);
        assertThat(result.hasBody()).isTrue();

    }

}
