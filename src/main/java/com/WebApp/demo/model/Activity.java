package com.WebApp.demo.model;

import java.time.LocalDateTime;

import jakarta.persistence.*;

@Entity
@Table(name = "activity")
public class Activity {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "activity_id")
	private Long id;
	
	 @ManyToOne(fetch = FetchType.LAZY)
	    @JoinColumn(name = "trip_id")
	    private Trip trip;

	    @Column(name = "name")
	    private String name;
	    
	    @Column(name = "time")
	    private LocalDateTime time;
	    
	    @Column(name = "location")
	    private String location;

	    public Activity() {
	    	
	    }
	    
	    public Activity(Trip trip, String name, String location, LocalDateTime time) {
	        this.trip = trip;
	        this.name = name;
	        this.location = location;
	        this.time = time;
	       
	    }
	    
		public Long getId() {
			return id;
		}

		public void setId(Long id) {
			this.id = id;
		}

		public Trip getTrip() {
			return trip;
		}

		public void setTrip(Trip trip) {
			this.trip = trip;
		}

		public String getName() {
			return name;
		}

		public void setName(String name) {
			this.name = name;
		}

		public LocalDateTime getTime() {
			return time;
		}

		public void setTime(LocalDateTime time) {
			this.time = time;
		}

		public String getLocation() {
			return location;
		}

		public void setLocation(String location) {
			this.location = location;
		}
	    
	    


}
