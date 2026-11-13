package com.riderent;
import org.springframework.data.jpa.repository.*;import org.springframework.data.repository.query.Param;import java.time.LocalDate;import java.util.*;
interface VehicleRepo extends JpaRepository<Vehicle,Long>{}
interface CustomerRepo extends JpaRepository<Customer,Long>{}
interface BookingRepo extends JpaRepository<Booking,Long>{ boolean existsByVehicleIdAndStatus(Long id,String status); List<Booking> findByCustomerIdOrderByStartDateDesc(Long id); @Query("select count(b)>0 from Booking b where b.vehicleId=:id and b.status='CONFIRMED' and b.startDate<=:end and b.endDate>=:start") boolean overlaps(@Param("id") Long id,@Param("start") LocalDate start,@Param("end") LocalDate end); }
