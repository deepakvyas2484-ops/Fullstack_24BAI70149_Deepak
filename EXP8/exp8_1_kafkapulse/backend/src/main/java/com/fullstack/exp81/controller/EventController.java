package com.fullstack.exp81.controller;
import com.fullstack.exp81.service.EventService;import com.fullstack.exp81.model.EventRecord;
import org.springframework.http.*;import org.springframework.web.bind.annotation.*;import java.util.*;
@RestController @RequestMapping("/api/events") @CrossOrigin(origins="http://localhost:3002")
public class EventController{
 private final EventService service;public EventController(EventService s){service=s;}
 @PostMapping public ResponseEntity<?> submit(@RequestBody Map<String,Object> b){
  try{return ResponseEntity.accepted().body(service.submit(b));}
  catch(EventService.DuplicateEventException e){return ResponseEntity.status(409).body(Map.of("error",e.getMessage()));}
  catch(IllegalArgumentException e){return ResponseEntity.badRequest().body(Map.of("error",e.getMessage()));}
  catch(Exception e){return ResponseEntity.internalServerError().body(Map.of("error","Publish failed: "+e.getMessage()));}
 }
 @GetMapping public List<EventRecord> all(){return service.all();}
 @GetMapping("/{id}") public ResponseEntity<?> one(@PathVariable String id){try{return ResponseEntity.ok(service.one(id));}catch(Exception e){return ResponseEntity.status(404).body(Map.of("error",e.getMessage()));}}
}