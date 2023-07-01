package com.WebApp.demo.model;

import java.time.LocalDate;
import java.util.Optional;
import java.util.Set;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.*;

@Entity
@Table(name = "Trip")
public class Trip {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@JsonIgnore
	@OneToMany(mappedBy = "trip")
	private Set<Collaborator> collaborators;
	
	@OneToMany(mappedBy = "trip")
	private Set<Flight> flights;
	
	@Column(name = "title")
	private String title;
	
	@Column(name = "description")
	private String description;

	 @Column(name = "start_date")
	private LocalDate startDate;
	
	@Column(name = "end_date")
	private LocalDate endDate;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Set<Collaborator> getCollaborators() {
		return collaborators;
	}

	public void setCollaborators(Set<Collaborator> collaborators) {
		this.collaborators = collaborators;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public LocalDate getStartDate() {
		return startDate;
	}

	public void setStartDate(LocalDate startDate) {
		this.startDate = startDate;
	}

	public LocalDate getEndDate() {
		return endDate;
	}

	public void setEndDate(LocalDate endDate) {
		this.endDate = endDate;
	}
	
    public User getOwner() {
    	 for (Collaborator collaborator : collaborators) {
    	        if (collaborator.getAccessLevel().equals("Owner")) {
    	            return collaborator.getUser();
    	        }
    	    }
  

        return null; // or throw an exception if an owner is required
    }
    

	public Set<Flight> getFlights() {
		return flights;
	}

	public void setFlights(Set<Flight> flights) {
		this.flights = flights;
	}



	
	
	
	
}
