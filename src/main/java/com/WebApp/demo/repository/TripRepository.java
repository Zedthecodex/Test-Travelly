package com.WebApp.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.WebApp.demo.model.Trip;

public interface TripRepository extends JpaRepository<Trip, Long>{

}
