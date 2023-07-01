package com.WebApp.demo.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.WebApp.demo.model.Booking;
import com.WebApp.demo.model.Collaborator;
import com.WebApp.demo.model.Flight;
import com.WebApp.demo.model.Trip;
import com.WebApp.demo.model.User;
import com.WebApp.demo.repository.BookingRepository;
import com.WebApp.demo.repository.TripRepository;

import jakarta.persistence.EntityNotFoundException;
import jakarta.servlet.http.HttpSession;

@RestController
@RequestMapping("/bookings")
public class BookingController {
	
	@Autowired
	BookingRepository bookingRepository;
	
	@Autowired
	TripRepository tripRepository;

	@PostMapping("/{tripId}")
	public ResponseEntity<String> addBookingToTrip(
			@PathVariable Long tripId,
			@RequestBody Booking booking,
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
		
		booking.setTrip(trip);
		bookingRepository.save(booking);
		
		
		return ResponseEntity.status(HttpStatus.CREATED).body("Booking added to trip successfully");
	
	}
	
	@GetMapping("/{tripid}")
	public ResponseEntity<List<Booking>> getBookingByTripId(
			@PathVariable Long tripId)
	{
		Trip trip = tripRepository.findById(tripId)
	            .orElseThrow(() -> new EntityNotFoundException("Trip not found"));

	    List<Booking> bookings = bookingRepository.findByTrip(trip);

	    return ResponseEntity.ok(bookings);
	}
	
	@DeleteMapping("/delete/{tripId}/{bookingId}")
	public ResponseEntity<String> deleteFlight(@PathVariable Long bookingId,
			@PathVariable Long tripId,
			HttpSession session) {
	    
		Trip trip = tripRepository.findById(tripId)
				.orElseThrow(() -> new  EntityNotFoundException("Trip not found"));
		
		Booking booking = bookingRepository.findById(bookingId)
	            .orElseThrow(() -> new EntityNotFoundException("Booking not found"));


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
		
	    bookingRepository.delete(booking);

	    return ResponseEntity.ok("Booking deleted successfully");
	}
	
	@PutMapping("/update/{bookingId}")
	public ResponseEntity<String> updateBooking(
	        @PathVariable Long bookingId,
	        @RequestBody Booking updatedBooking
	) {
	    Booking booking = bookingRepository.findById(bookingId)
	            .orElseThrow(() -> new EntityNotFoundException("Booking not found"));

	    booking.setBookingType(updatedBooking.getBookingType());
	    booking.setBookingDetails(updatedBooking.getBookingDetails());
	    booking.setCosts(updatedBooking.getCosts());
	   
	    bookingRepository.save(booking);

	    return ResponseEntity.ok("Booking updated successfully");
	}
	
}
