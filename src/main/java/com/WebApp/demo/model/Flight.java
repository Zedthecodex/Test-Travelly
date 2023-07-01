package com.WebApp.demo.model;

import java.time.LocalDateTime;

import jakarta.persistence.*;

@Entity
@Table(name ="flight")
public class Flight {

	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "trip_id")
	private Trip trip;
	
	@Column(name = "airline")
	private String airline;
	
	@Column(name = "departure_city") 
	private String departureCity;
	
	@Column(name = "arrival_city") 
	private String arrivalCity;
	
	@Column(name = "departure_date")
	private LocalDateTime departureDate;
	
	@Column(name = "arrival_date")
	private LocalDateTime arrivalDate;
	
	@Column(name ="flight_number")
	private String flightNumber;
	
	public Flight() {
		
	}

    public Flight(Trip trip, String departureCity, String arrivalCity, LocalDateTime departureDate,
                  LocalDateTime arrivalDate, String airline, String flightNumber) {
        this.trip = trip;
        this.departureCity = departureCity;
        this.arrivalCity = arrivalCity;
        this.departureDate = departureDate;
        this.arrivalDate = arrivalDate;
        this.airline = airline;
        this.flightNumber = flightNumber;
  
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



	public String getAirline() {
		return airline;
	}



	public void setAirline(String airline) {
		this.airline = airline;
	}



	public String getDepartureCity() {
		return departureCity;
	}



	public void setDepartureCity(String departureCity) {
		this.departureCity = departureCity;
	}



	public String getArrivalCity() {
		return arrivalCity;
	}



	public void setArrivalCity(String arrivalCity) {
		this.arrivalCity = arrivalCity;
	}



	public LocalDateTime getDepartureDate() {
		return departureDate;
	}



	public void setDepartureDate(LocalDateTime departureDate) {
		this.departureDate = departureDate;
	}



	public LocalDateTime getArrivalDate() {
		return arrivalDate;
	}



	public void setArrivalDate(LocalDateTime arrivalDate) {
		this.arrivalDate = arrivalDate;
	}



	public String getFlightNumber() {
		return flightNumber;
	}



	public void setFlightNumber(String flightNumber) {
		this.flightNumber = flightNumber;
	}
    
    
}
