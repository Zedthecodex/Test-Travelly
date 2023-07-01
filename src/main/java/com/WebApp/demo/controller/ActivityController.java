package com.WebApp.demo.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.WebApp.demo.model.Activity;
import com.WebApp.demo.model.Collaborator;
import com.WebApp.demo.model.Flight;
import com.WebApp.demo.model.Hotel;
import com.WebApp.demo.model.Trip;
import com.WebApp.demo.model.User;
import com.WebApp.demo.repository.ActivityRepository;
import com.WebApp.demo.repository.HotelRepository;
import com.WebApp.demo.repository.TripRepository;

import jakarta.persistence.EntityNotFoundException;
import jakarta.servlet.http.HttpSession;

@RestController
@RequestMapping("/activities")
public class ActivityController {

	@Autowired
	ActivityRepository activityRepository;
	
	@Autowired
	TripRepository tripRepository;
	
	@PostMapping("/{tripId}")
	public ResponseEntity<String> addActivityToTrip(
			@PathVariable Long tripId,
			@RequestBody Activity activity,
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
		
		
		activity.setTrip(trip);
		activityRepository.save(activity);
		
		return ResponseEntity.status(HttpStatus.CREATED).body("Activity added to trip successfully");
	
	}
	
	@GetMapping("/{tripid}")
	public ResponseEntity<List<Activity>> getactivitiesByTripId(
			@PathVariable Long tripId)
	{
		Trip trip = tripRepository.findById(tripId)
	            .orElseThrow(() -> new EntityNotFoundException("Trip not found"));

	    List<Activity> activities = activityRepository.findByTrip(trip);

	    return ResponseEntity.ok(activities);
	}
	
	@DeleteMapping("/delete/{tripId}/{activityId}")
	public ResponseEntity<String> deleteactivity(@PathVariable Long activityId,
			@PathVariable Long tripId,
			HttpSession session) {
	    
		Trip trip = tripRepository.findById(tripId)
				.orElseThrow(() -> new  EntityNotFoundException("Trip not found"));
		
		Activity activity = activityRepository.findById(activityId)
	            .orElseThrow(() -> new EntityNotFoundException("Activity not found"));


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
		
		activityRepository.save(activity);

	    return ResponseEntity.ok("Activity deleted successfully");
	}
	
	@PutMapping("/update/{activityId}")
	public ResponseEntity<String> updateActivity(
	        @PathVariable Long activityId,
	        @RequestBody Activity updatedActivity
	) {
	    Activity activity = activityRepository.findById(activityId)
	            .orElseThrow(() -> new EntityNotFoundException("Activity not found"));

	    activity.setName(updatedActivity.getName());
	    activity.setTime(updatedActivity.getTime());
	    activity.setLocation(updatedActivity.getLocation());
	    
	    activityRepository.save(activity);

	    return ResponseEntity.ok("Actvitiy updated successfully");
	}
	
}
