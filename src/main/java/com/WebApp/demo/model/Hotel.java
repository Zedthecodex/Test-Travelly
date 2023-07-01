package com.WebApp.demo.model;

import java.time.LocalDateTime;

import jakarta.persistence.*;

@Entity
@Table(name ="hotel")
public class Hotel {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY) 
	@Column(name = "hotel_id")
	private Long id;
	
	@ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "trip_id")
    private Trip trip;

    @Column(name = "name")
    private String name;

    @Column(name = "address")
    private String address;

    @Column(name = "check_in_date")
    private LocalDateTime checkInDate;

    @Column(name = "check_out_date")
    private LocalDateTime checkOutDate;
	
    public Hotel() {
    }
    
    public Hotel(Trip trip, String name, String address, LocalDateTime checkInDate, LocalDateTime checkOutDate) {
        this.trip = trip;
        this.name = name;
        this.address = address;
        this.checkInDate = checkInDate;
        this.checkOutDate = checkOutDate;
  
    }

    public Long getId() {
        return id;
    }

    public void setid(Long id) {
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

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public LocalDateTime getCheckInDate() {
        return checkInDate;
    }

    public void setCheckInDate(LocalDateTime checkInDate) {
        this.checkInDate = checkInDate;
    }

    public LocalDateTime getCheckOutDate() {
        return checkOutDate;
    }

    public void setCheckOutDate(LocalDateTime checkOutDate) {
        this.checkOutDate = checkOutDate;
    }
    

   
}
