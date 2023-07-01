package com.WebApp.demo.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.WebApp.demo.model.Booking;
import com.WebApp.demo.model.Trip;

public interface BookingRepository extends JpaRepository<Booking,Long>{

	List<Booking> findByTrip(Trip trip);
}
