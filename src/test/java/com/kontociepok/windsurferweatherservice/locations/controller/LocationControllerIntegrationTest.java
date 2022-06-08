package com.kontociepok.windsurferweatherservice.locations.controller;

import com.kontociepok.windsurferweatherservice.locations.model.LocationCoordinates;
import com.kontociepok.windsurferweatherservice.locations.repository.LocationCoordinatesRepo;
import com.kontociepok.windsurferweatherservice.locations.service.LocationService;
import org.hamcrest.MatcherAssert;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.test.context.ActiveProfiles;

import java.time.LocalDate;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.assertThrows;


@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
public class LocationControllerIntegrationTest {

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
        restTemplate.delete("http://localhost:" + port + "/coordinates?coordinatesId=6",
                LocationCoordinatesResponse.class);

        //then
        var result = restTemplate.getForEntity("http://localhost:" + port + "/coordinates",
                LocationCoordinatesResponse[].class);
        assertThat(result.getStatusCodeValue() == 200);
        assertThat(result.hasBody()).isTrue();
        assertThat(result.getBody()).hasSize(5);

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
    void shouldReturnExeptionWhenBadFormatDate(){
        //given
        String dateToday = "2002.2.2";

        //when
        Exception ex = assertThrows(Exception.class, () -> locationService.checkDate(dateToday));

        //then
        MatcherAssert.assertThat(ex.getMessage(), is("Text '2002.2.2' could not be parsed at index 4"));

    }


}
