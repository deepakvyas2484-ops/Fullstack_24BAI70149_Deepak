package com.fullstack.exp81.service;
import com.fullstack.exp81.model.EventRecord;import com.fullstack.exp81.repo.EventRecordRepository;
import org.springframework.beans.factory.annotation.Value;import org.springframework.kafka.core.KafkaTemplate;import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;import java.util.*;
@Service public class EventService {
 private final EventRecordRepository repo;private final KafkaTemplate<String,String> kafka;private final com.fasterxml.jackson.databind.ObjectMapper mapper;private final String topic;
 public EventService(EventRecordRepository r,KafkaTemplate<String,String> k,com.fasterxml.jackson.databind.ObjectMapper m,@Value("${app.kafka.main-topic}") String t){repo=r;kafka=k;mapper=m;topic=t;}
 @Transactional public EventRecord submit(Map<String,Object> b)throws Exception{
  String id=String.valueOf(b.getOrDefault("eventId","")).trim(),content=String.valueOf(b.getOrDefault("content","")).trim();
  int failures=b.get("failuresBeforeSuccess")==null?0:Integer.parseInt(b.get("failuresBeforeSuccess").toString());
  if(id.isBlank()||content.isBlank())throw new IllegalArgumentException("eventId and content are required");
  if(failures<0||failures>20)throw new IllegalArgumentException("Failures must be between 0 and 20");
  if(repo.existsById(id))throw new DuplicateEventException("Duplicate event ID: "+id);
  EventRecord r=repo.save(new EventRecord(id,content,failures));Map<String,Object> msg=new HashMap<>();msg.put("eventId",id);msg.put("content",content);msg.put("failuresBeforeSuccess",failures);
  kafka.send(topic,id,mapper.writeValueAsString(msg)).get();return r;
 }
 public List<EventRecord> all(){return repo.findAll();}
 public EventRecord one(String id){return repo.findById(id).orElseThrow(()->new NoSuchElementException("Event not found"));}
 public void process(Map<String,Object> msg){
  String id=String.valueOf(msg.get("eventId"));EventRecord r=repo.findById(id).orElse(null);if(r==null||"PROCESSED".equals(r.getStatus()))return;
  int attempt=r.getAttempts()+1;r.setAttempts(attempt);r.setStatus("PROCESSING");r.setLastError(null);repo.save(r);
  if(attempt<=r.getFailuresBeforeSuccess()){r.setStatus("RETRYING");r.setLastError("Simulated transient failure on attempt "+attempt);repo.save(r);throw new RuntimeException("Simulated transient failure");}
  r.setStatus("PROCESSED");r.setLastError(null);repo.save(r);
 }
 @Transactional public void markDlq(Map<String,Object> msg){repo.findById(String.valueOf(msg.get("eventId"))).ifPresent(r->{r.setStatus("DLQ");r.setLastError("Retry limit exhausted; routed to DLQ");repo.save(r);});}
 public static class DuplicateEventException extends RuntimeException{public DuplicateEventException(String m){super(m);}}
}