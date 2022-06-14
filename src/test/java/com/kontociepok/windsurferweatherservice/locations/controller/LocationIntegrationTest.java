package com.kontociepok.windsurferweatherservice.locations.controller;

import com.kontociepok.windsurferweatherservice.locations.exception.CustomBadFormatDate;
import com.kontociepok.windsurferweatherservice.locations.exception.CustomParameterConstraintException;
import com.kontociepok.windsurferweatherservice.locations.model.LocationCoordinates;
import com.kontociepok.windsurferweatherservice.locations.repository.LocationCoordinatesRepo;
import com.kontociepok.windsurferweatherservice.locations.service.LocationService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;

import java.time.LocalDate;

import static org.assertj.core.api.Assertions.assertThat;


@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class LocationIntegrationTest {

    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    @Autowired
    private LocationCoordinatesRepo locationCoordinatesRepo;

    @Autowired
    private LocationService locationService;


    @Test
    void shouldReturnAllCoordinates(){

            // given
            var expected = new LocationCoordinatesResponse(1L, 54.7028, 18.6707);

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
        var expected = new LocationCoordinatesResponse(6L,11.11,22.22);

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

        //when
        restTemplate.delete("http://localhost:" + port + "/coordinates?coordinatesId=5",
                LocationCoordinatesResponse.class);

        //then
        var result = restTemplate.getForEntity("http://localhost:" + port + "/coordinates",
                LocationCoordinatesResponse[].class);
        assertThat(result.getStatusCodeValue() == 200);
        assertThat(result.hasBody()).isTrue();
        assertThat(result.getBody()).hasSize(4);

    }

    @Test
    void shouldReturnListBestPlaceWhenExists(){
        //given
        locationCoordinatesRepo.deleteAll();
        locationCoordinatesRepo.save(new LocationCoordinates(13.0968, -59.6144));

        String dateToday = LocalDate.now().toString();

        //when
        var result = restTemplate.getForEntity("http://localhost:" + port + "/date?date="+dateToday,
                LocationResponse[].class);

        //then
        assertThat(result.getStatusCodeValue() == 200);
        assertThat(result.hasBody()).isTrue();
        assertThat(result.getBody()).hasSize(1);

    }
    @Test
    void shouldReturnExceptionWhenWriteBadFormatDate() {
        //when
        var result = restTemplate.getForEntity("http://localhost:" + port + "/date?date=2022-06.10",
                CustomBadFormatDate.class);

        //then
        assertThat(result.getStatusCodeValue() == 400);
        assertThat(result.hasBody()).isTrue();
        assertThat(result.getBody().getMessage()).isEqualTo("Bad format date: Text '2022-06.10' could not be parsed at index 7");
    }

    @Test
    void shouldReturnExceptionWhenWriteOldOrFutureDate(){
        //when
        var result = restTemplate.getForEntity("http://localhost:" + port + "/date?date=2021-06-10",
                CustomParameterConstraintException.class);

        //then
        assertThat(result.getStatusCodeValue() == 500);
        assertThat(result.hasBody()).isTrue();
        assertThat(result.getBody().getMessage()).isEqualTo("Date is too old or too far away");
    }


}
