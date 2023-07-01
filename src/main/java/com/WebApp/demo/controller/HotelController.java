package com.WebApp.demo.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.WebApp.demo.model.Collaborator;
import com.WebApp.demo.model.Flight;
import com.WebApp.demo.model.Hotel;
import com.WebApp.demo.model.Trip;
import com.WebApp.demo.model.User;
import com.WebApp.demo.repository.HotelRepository;
import com.WebApp.demo.repository.TripRepository;

import jakarta.persistence.EntityNotFoundException;
import jakarta.servlet.http.HttpSession;

@RestController
@RequestMapping("/hotels")
public class HotelController {

	@Autowired 
	HotelRepository hotelRepository;
	
	@Autowired
	TripRepository tripRepository;
	
	@PostMapping("/{tripId}")
	public ResponseEntity<String> addHotelToTrip(
			@PathVariable Long tripId,
			@RequestBody Hotel hotel,
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
		
		hotel.setTrip(trip);
		hotelRepository.save(hotel);
	
		
		return ResponseEntity.status(HttpStatus.CREATED).body("Hotel added to trip successfully");
	
	}
	
	@GetMapping("/{tripid}")
	public ResponseEntity<List<Hotel>> getHotelsByTripId(
			@PathVariable Long tripId)
	{
		Trip trip = tripRepository.findById(tripId)
	            .orElseThrow(() -> new EntityNotFoundException("Trip not found"));

		List<Hotel> hotels = hotelRepository.findByTrip(trip);
	

	    return ResponseEntity.ok(hotels);
	}
	
	@DeleteMapping("/delete/{tripId}/{hotelId}")
	public ResponseEntity<String> deleteHotel(@PathVariable Long hotelId,
			@PathVariable Long tripId,
			HttpSession session) {
	    
		Trip trip = tripRepository.findById(tripId)
				.orElseThrow(() -> new  EntityNotFoundException("Trip not found"));
		
		Hotel hotel = hotelRepository.findById(hotelId)
	            .orElseThrow(() -> new EntityNotFoundException("Hotel not found"));


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
		
	    hotelRepository.delete(hotel);

	    return ResponseEntity.ok("Hotel deleted successfully");
	}
	
	@PutMapping("/update/{hotelId}")
	public ResponseEntity<String> updateHotel(
	        @PathVariable Long hotelId,
	        @RequestBody Hotel updatedHotel
	) {
	    Hotel hotel = hotelRepository.findById(hotelId)
	            .orElseThrow(() -> new EntityNotFoundException("Hotel not found"));

	    hotel.setName(updatedHotel.getName());
	    hotel.setAddress(updatedHotel.getAddress());
	    hotel.setCheckInDate(updatedHotel.getCheckInDate());
	    hotel.setCheckOutDate(updatedHotel.getCheckOutDate());
	    
	    hotelRepository.save(hotel);
	    
	    return ResponseEntity.ok("Hotel updated successfully");
	}
}
