package com.WebApp.demo.model;

import com.WebApp.demo.model.Trip;

import jakarta.persistence.*;

@Entity
@Table(name= "booking")
public class Booking {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "booking_id")
	private Long id;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "trip_id")
	private Trip trip;

	@Column(name = "booking_type")
    private String bookingType;

    @Column(name = "booking_details")
    private String bookingDetails;
    
    @Column(name = "costs")
    private Float costs;
    
    public Booking() {
    	
    }
    
    public Booking(Trip trip, String bookingType, String bookingDetails, Float costs) {
        this.trip = trip;
        this.bookingType = bookingType;
        this.bookingDetails = bookingDetails;
        this.costs = costs;
    
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

	public String getBookingType() {
		return bookingType;
	}

	public void setBookingType(String bookingType) {
		this.bookingType = bookingType;
	}

	public String getBookingDetails() {
		return bookingDetails;
	}

	public void setBookingDetails(String bookingDetails) {
		this.bookingDetails = bookingDetails;
	}

	public Float getCosts() {
		return costs;
	}

	public void setCosts(Float costs) {
		this.costs = costs;
	}
    
    
}
