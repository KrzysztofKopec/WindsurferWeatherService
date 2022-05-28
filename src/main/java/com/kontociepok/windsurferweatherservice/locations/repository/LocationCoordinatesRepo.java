package com.kontociepok.windsurferweatherservice.locations.repository;

import com.kontociepok.windsurferweatherservice.locations.model.LocationCoordinates;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LocationCoordinatesRepo extends JpaRepository<LocationCoordinates, Long> {
}
