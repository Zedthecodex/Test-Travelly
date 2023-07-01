package com.WebApp.demo.controller;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.WebApp.demo.model.Collaborator;
import com.WebApp.demo.model.Trip;
import com.WebApp.demo.model.User;
import com.WebApp.demo.repository.CollaboratorRepository;
import com.WebApp.demo.repository.TripRepository;
import com.WebApp.demo.repository.UserRepository;

import jakarta.persistence.EntityNotFoundException;
import jakarta.servlet.http.HttpSession;

@RestController
public class TripController {

	@Autowired
	UserRepository userRepository;
	
	@Autowired 
	TripRepository tripRepository;
	
	@Autowired
	CollaboratorRepository collaboratorRepository;
	
	@PostMapping("/users/{userId}/trips")
	Trip newTrip(@PathVariable("userId") Long userId,
			@RequestBody Trip newTrip)
	{
		 Set<Collaborator> collaborators = newTrip.getCollaborators();
		    
		    if (collaborators == null) {
		        collaborators = new HashSet<>();
		        
		    }
		 
		User initialUser = userRepository.findById(userId).get();
		Collaborator initialCollaborator = 
				new Collaborator(initialUser,newTrip,"Owner");
		
		collaborators.add(initialCollaborator);
		
		Trip createdTrip = tripRepository.save(newTrip);
		
		
		collaboratorRepository.saveAll(collaborators);
		
		return createdTrip;
	}
	
	@GetMapping("/{userId}/trips")
	public List<Trip> getTripsByUserId(@PathVariable("userId") Long userId)
	{
		List<Collaborator> collaborations = collaboratorRepository.findByUserId(userId);
		
		List<Trip> trips = new ArrayList<>();
		
		 for (Collaborator collaboration : collaborations) {
		        Trip trip = collaboration.getTrip();
		        trips.add(trip);
		    }
		 return trips;
	}
	
	
	@PutMapping("/{tripId}/collaborators")
	public ResponseEntity<String> addCollaboratorToTrip(
			@PathVariable("tripId") Long tripId, 
			@RequestParam Long userId,
			@RequestParam String accessLevel,
//			@RequestBody Collaborator collaborator,
			HttpSession session) { 
		
		User currentUser = (User) session.getAttribute("user");
        if (currentUser == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("User not authenticated");
        }
        
        String currentname = currentUser.getUsername();
        
     // Check if the current user is the owner of the trip
        Trip trip = tripRepository.findById(tripId)
            .orElseThrow(() -> new EntityNotFoundException("Trip not found"));
        if (!trip.getOwner().getUsername().equals(currentname)) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Only the owner ( " + trip.getOwner().getUsername() + " ) can add collaborators");
        }
        
        //Add Collaborator to the trip
        User user = userRepository.findById(userId)
        		.orElseThrow(() -> new EntityNotFoundException("User not found"));
        Collaborator newcollaborator  = 
				new Collaborator(user,trip,accessLevel);
//        collaborator.setTrip(trip);
//        collaborator.setUser(newCollaborator);
//        collaborator.setAccessLevel(accessLevel);
        
        collaboratorRepository.save(newcollaborator);
        
        return ResponseEntity.ok("Collaborator added successfully");
        
        

	}
}
