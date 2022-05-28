package com.kontociepok.windsurferweatherservice;

import com.kontociepok.windsurferweatherservice.locations.model.LocationCoordinates;
import com.kontociepok.windsurferweatherservice.locations.repository.LocationCoordinatesRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class WindsurferweatherserviceApplication implements CommandLineRunner {

	@Autowired
	LocationCoordinatesRepo locationCoordinatesRepo;

	public static void main(String[] args) {
		SpringApplication.run(WindsurferweatherserviceApplication.class, args);
	}

	@Override
	public void run(String... args){

		LocationCoordinates locationCoordinates = new LocationCoordinates(54.7028, 18.6707);
		LocationCoordinates locationCoordinates1 = new LocationCoordinates(13.0968, -59.6144);
		LocationCoordinates locationCoordinates2 = new LocationCoordinates(-3.7327, -38.5269);
		LocationCoordinates locationCoordinates3 = new LocationCoordinates(34.6702, 32.7011);
		LocationCoordinates locationCoordinates4 = new LocationCoordinates(-20.4695, 57.3442);

		locationCoordinatesRepo.save(locationCoordinates);
		locationCoordinatesRepo.save(locationCoordinates1);
		locationCoordinatesRepo.save(locationCoordinates2);
		locationCoordinatesRepo.save(locationCoordinates3);
		locationCoordinatesRepo.save(locationCoordinates4);

	}

}
