package com.kontociepok.windsurferweatherservice.locations.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.kontociepok.windsurferweatherservice.locations.model.LocationCoordinates;
import com.kontociepok.windsurferweatherservice.locations.repository.LocationCoordinatesRepo;
import com.kontociepok.windsurferweatherservice.locations.service.LocationService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import java.util.List;

import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@WebMvcTest(LocationController.class)
@ActiveProfiles("test")
class LocationControllerMockMvcTest {

    @Autowired
    private MockMvc mvc;

    @MockBean
    private LocationCoordinatesRepo locationCoordinatesRepo;

    @MockBean
    private LocationService locationService;

    @MockBean
    private WeatherBitClientStub weatherBitClientStub;

    @Test
    void shouldReturnAllCoordinatesWhenExists() throws Exception{
        //given
        LocationCoordinatesResponse locationCoordinates = new LocationCoordinatesResponse(1L,12.34,56.78);
        List<LocationCoordinatesResponse> coordinates = List.of(locationCoordinates);
        given(locationService.allCoordinates()).willReturn(coordinates);

        // when
        ResultActions result = mvc.perform(get("/coordinates").contentType(MediaType.APPLICATION_JSON));

        // then
        result.andExpect(status().is(200))
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].lat", is(locationCoordinates.getLat())))
                .andExpect(jsonPath("$[0].lon", is(locationCoordinates.getLon())));

    }
    @Test
    void shouldReturnSaveCoordinates() throws Exception{
        //given
        LocationCoordinatesResponse locationCoordinates = new LocationCoordinatesResponse(1L,12.34,56.78);
        given(locationService.addCoordinate(12.34,56.78)).willReturn(locationCoordinates);

        //when
        ResultActions result = mvc.perform(post("/coordinates?lat=12.34&lon=56.78").contentType(MediaType.APPLICATION_JSON));

        //then
        result.andExpect(status().is(200))
                .andExpect(jsonPath("$.lat").value(12.34))
                .andExpect(jsonPath("$.lon").value(56.78));
    }
    @Test
    void shouldReturnDeleteCoordinates() throws Exception{
        //given
        locationService.addCoordinate(12.34,56.78);
        locationService.addCoordinate(10D,10D);
        LocationCoordinatesResponse locationCoordinates = new LocationCoordinatesResponse(1L,12.34,56.78);
        given(locationService.deleteCoordinates(1L)).willReturn(locationCoordinates);

        //when
        ResultActions result = mvc.perform(delete("/coordinates?coordinatesId=1"));

        //then
        result.andExpect(status().is(200))
                .andExpect(jsonPath("$.lat", is(locationCoordinates.getLat())))
                .andExpect(jsonPath("$.lon", is(locationCoordinates.getLon())));

    }

}