package com.WebApp.demo.model;

import jakarta.persistence.*;

@Entity
@Table(name = "Collaborator")
public class Collaborator {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@ManyToOne
	@JoinColumn(name = "user_id")
	private User user;
	
	@ManyToOne
	@JoinColumn(name = "trip_id")
	private Trip trip;
	
	@Column(name = "access_level")
	private String accessLevel;
	
	public Collaborator() {
    }

    public Collaborator(User user, Trip trip, String accessLevel) {
        this.user = user;
        this.trip = trip;
        this.accessLevel = accessLevel;
    }

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public Trip getTrip() {
		return trip;
	}

	public void setTrip(Trip trip) {
		this.trip = trip;
	}

	public String getAccessLevel() {
		return accessLevel;
	}

	public void setAccessLevel(String accessLevel) {
		this.accessLevel = accessLevel;
	}
    
    
}
