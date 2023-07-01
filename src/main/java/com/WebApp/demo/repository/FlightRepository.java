package com.WebApp.demo.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.WebApp.demo.model.Flight;
import com.WebApp.demo.model.Trip;

public interface FlightRepository extends JpaRepository<Flight,Long> {
	List<Flight> findByTrip(Trip trip);
}
