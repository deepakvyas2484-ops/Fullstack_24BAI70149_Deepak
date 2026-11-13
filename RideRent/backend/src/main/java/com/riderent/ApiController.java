package com.riderent;
import org.springframework.web.bind.annotation.*;import org.springframework.http.*;import org.springframework.beans.factory.annotation.Autowired;import java.time.*;import java.util.*;
@RestController @RequestMapping("/api") @CrossOrigin(origins="http://localhost:3006")
public class ApiController {
 @Autowired VehicleRepo vehicles; @Autowired CustomerRepo customers; @Autowired BookingRepo bookings;
 @GetMapping("/vehicles") public List<Vehicle> vehicles(){return vehicles.findAll();}
 @PostMapping("/vehicles") public Vehicle add(@RequestBody Vehicle v){v.id=null;return vehicles.save(v);}
 @PutMapping("/vehicles/{id}") public Vehicle edit(@PathVariable Long id,@RequestBody Vehicle v){vehicles.findById(id).orElseThrow();v.id=id;return vehicles.save(v);}
 @DeleteMapping("/vehicles/{id}") public ResponseEntity<?> delete(@PathVariable Long id){if(bookings.existsByVehicleIdAndStatus(id,"CONFIRMED"))return ResponseEntity.status(409).body(Map.of("error","Vehicle has active bookings"));vehicles.deleteById(id);return ResponseEntity.noContent().build();}
 @GetMapping("/customers") public List<Customer> customers(){return customers.findAll();}
 @PostMapping("/customers") public Customer addCustomer(@RequestBody Customer c){c.id=null;return customers.save(c);}
 @GetMapping("/availability") public Map<String,Object> available(@RequestParam Long vehicleId,@RequestParam String start,@RequestParam String end){LocalDate s=LocalDate.parse(start),e=LocalDate.parse(end);if(e.isBefore(s))throw new IllegalArgumentException("End date must not precede start date");return Map.of("available",vehicles.existsById(vehicleId)&&!bookings.overlaps(vehicleId,s,e));}
 @PostMapping("/bookings") public ResponseEntity<?> book(@RequestBody Booking b){if(!vehicles.existsById(b.vehicleId)||!customers.existsById(b.customerId))return ResponseEntity.badRequest().body(Map.of("error","Choose an existing vehicle and customer"));if(b.startDate==null||b.endDate==null||b.endDate.isBefore(b.startDate))return ResponseEntity.badRequest().body(Map.of("error","Invalid dates"));if(bookings.overlaps(b.vehicleId,b.startDate,b.endDate))return ResponseEntity.status(409).body(Map.of("error","Vehicle unavailable for selected dates"));Vehicle v=vehicles.findById(b.vehicleId).orElseThrow();b.totalAmount=(java.time.temporal.ChronoUnit.DAYS.between(b.startDate,b.endDate)+1)*v.pricePerDay;b.status="CONFIRMED";b.id=null;return ResponseEntity.ok(bookings.save(b));}
 @GetMapping("/bookings") public List<Booking> bookings(){return bookings.findAll();}
 @GetMapping("/customers/{id}/history") public List<Booking> history(@PathVariable Long id){return bookings.findByCustomerIdOrderByStartDateDesc(id);}
 @PatchMapping("/bookings/{id}/cancel") public Booking cancel(@PathVariable Long id){Booking b=bookings.findById(id).orElseThrow();b.status="CANCELLED";return bookings.save(b);}
 @GetMapping("/dashboard") public Map<String,Object> dashboard(){List<Booking> all=bookings.findAll();return Map.of("vehicles",vehicles.count(),"customers",customers.count(),"bookings",all.size(),"revenue",all.stream().filter(b->"CONFIRMED".equals(b.status)).mapToDouble(b->b.totalAmount).sum());}
 @RestControllerAdvice static class Errors {@ExceptionHandler(Exception.class) ResponseEntity<?> error(Exception e){return ResponseEntity.badRequest().body(Map.of("error",e.getMessage()==null?"Request failed":e.getMessage()));}}
}
