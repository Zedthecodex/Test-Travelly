package com.WebApp.demo.controller;

import java.security.Principal;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.WebApp.demo.model.Collaborator;
import com.WebApp.demo.model.Flight;
import com.WebApp.demo.model.Trip;
import com.WebApp.demo.model.User;
import com.WebApp.demo.repository.FlightRepository;
import com.WebApp.demo.repository.TripRepository;

import jakarta.persistence.EntityNotFoundException;
import jakarta.servlet.http.HttpSession;

@RestController
@RequestMapping("/flights")
public class FlightController {

	@Autowired
	FlightRepository flightRepository;
	
	@Autowired
	TripRepository tripRepository;
	
	@PostMapping("/{tripId}")
	public ResponseEntity<String> addFlightToTrip(
			@PathVariable Long tripId,
			@RequestBody Flight flight,
			HttpSession session
			){
		Trip trip = tripRepository.findById(tripId)
				.orElseThrow(() -> new  EntityNotFoundException("Trip not found"));
		
		
//		if(principal.getName() == null) 
//		{
//			throw new RuntimeException("You have not logged in and is trying to access this");
//		}
//		
//		String currentUser = principal.getName();
		
		User currentUser = (User) session.getAttribute("user");
        if (currentUser == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("User not authenticated");
        }
		
		Collaborator collaborator = trip.getCollaborators()
				.stream()
				.filter(c -> c.getUser().getUsername().equals(currentUser.getUsername()))
				.findFirst()
				.orElseThrow(() -> new RuntimeException("User is not authorized"));
		
		if(collaborator.getAccessLevel() == "Viewer") 
		{
			 throw new RuntimeException("User " + collaborator.getAccessLevel()  + "access level is only at Viewer, can't make changes");
		}
		
		
		flight.setTrip(trip);
		flightRepository.save(flight);
		
		return ResponseEntity.status(HttpStatus.CREATED).body("Flight added to trip successfully");
	
	}
	
	@GetMapping("/{tripid}")
	public ResponseEntity<List<Flight>> getFlightsByTripId(
			@PathVariable Long tripId)
	{
		Trip trip = tripRepository.findById(tripId)
	            .orElseThrow(() -> new EntityNotFoundException("Trip not found"));

	    List<Flight> flights = flightRepository.findByTrip(trip);

	    return ResponseEntity.ok(flights);
	}
	
	@DeleteMapping("/{flightId}")
	public ResponseEntity<String> deleteFlight(@PathVariable Long flightId,
			HttpSession session) {
	    
		
		
		Flight flight = flightRepository.findById(flightId)
	            .orElseThrow(() -> new EntityNotFoundException("Flight not found"));

//		User currentUser = (User) session.getAttribute("user");
//        if (currentUser == null) {
//            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("User not authenticated");
//        }
//        
//        Collaborator collaborator = currentUser.getCollaborators()
//				.stream()
//				.filter(c -> c.getUser().getUsername().equals(currentUser.getUsername()))
//				.findFirst()
//				.orElseThrow(() -> new RuntimeException("User is not authorized"));
//		
//		if(collaborator.getAccessLevel() == "Viewer") 
//		{
//			 throw new RuntimeException("User " + collaborator.getAccessLevel()  + "access level is only at Viewer, can't make changes");
//		}
//		
	    flightRepository.delete(flight);

	    return ResponseEntity.ok("Flight deleted successfully");
	}
	
	@PutMapping("/{flightId}")
	public ResponseEntity<String> updateFlight(
	        @PathVariable Long flightId,
	        @RequestBody Flight updatedFlight
	) {
	    Flight flight = flightRepository.findById(flightId)
	            .orElseThrow(() -> new EntityNotFoundException("Flight not found"));

	    flight.setAirline(updatedFlight.getAirline());
	    flight.setDepartureCity(updatedFlight.getDepartureCity());
	    flight.setArrivalCity(updatedFlight.getArrivalCity());
	    flight.setDepartureDate(updatedFlight.getDepartureDate());
	    flight.setArrivalDate(updatedFlight.getArrivalDate());

	    flightRepository.save(flight);

	    return ResponseEntity.ok("Flight updated successfully");
	}
	
}
