package com.WebApp.demo.controller;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.WebApp.demo.model.Collaborator;
import com.WebApp.demo.model.Trip;
import com.WebApp.demo.model.User;
import com.WebApp.demo.repository.CollaboratorRepository;
import com.WebApp.demo.repository.TripRepository;
import com.WebApp.demo.repository.UserRepository;

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
	
	
	@PutMapping("/users/{ownerId}/{userId}/trips/{tripId}")
}
