package com.WebApp.demo.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.WebApp.demo.model.Activity;
import com.WebApp.demo.model.Trip;

public interface ActivityRepository extends JpaRepository<Activity,Long>{

	List<Activity> findByTrip(Trip trip);
}
