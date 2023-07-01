package com.WebApp.demo.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.WebApp.demo.model.Hotel;
import com.WebApp.demo.model.Trip;

public interface HotelRepository extends JpaRepository<Hotel,Long> {

	List<Hotel> findByTrip(Trip trip);
}
