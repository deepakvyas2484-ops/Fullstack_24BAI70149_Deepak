package com.riderent;
import jakarta.persistence.*;
import java.time.LocalDate;
@Entity class Vehicle { @Id @GeneratedValue(strategy=GenerationType.IDENTITY) public Long id; public String name,type,brand,registrationNumber,imageUrl,transmission,fuel,status="ACTIVE"; public double pricePerDay; public int seats; public Vehicle(){} }
@Entity class Customer { @Id @GeneratedValue(strategy=GenerationType.IDENTITY) public Long id; public String name,email,phone,licenseNumber; public Customer(){} }
@Entity class Booking { @Id @GeneratedValue(strategy=GenerationType.IDENTITY) public Long id; public Long vehicleId,customerId; public LocalDate startDate,endDate; public double totalAmount; public String status="CONFIRMED"; public Booking(){} }
