package com.fullstack.exp82.model;
import jakarta.persistence.*;
import java.time.LocalDateTime;
@Entity @Table(name="event_records")
public class EventRecord {
 @Id private String eventId;
 @Column(nullable=false,length=4000) private String content;
 private String status; private int attempts; private int failuresBeforeSuccess;
 @Column(length=2000) private String lastError;
 private LocalDateTime createdAt,updatedAt;
 protected EventRecord(){}
 public EventRecord(String id,String content,int failures){this.eventId=id;this.content=content;this.failuresBeforeSuccess=failures;this.status="QUEUED";this.createdAt=LocalDateTime.now();this.updatedAt=createdAt;}
 public String getEventId(){return eventId;} public String getContent(){return content;} public String getStatus(){return status;}
 public int getAttempts(){return attempts;} public int getFailuresBeforeSuccess(){return failuresBeforeSuccess;}
 public String getLastError(){return lastError;} public LocalDateTime getCreatedAt(){return createdAt;} public LocalDateTime getUpdatedAt(){return updatedAt;}
 public void setStatus(String s){status=s;updatedAt=LocalDateTime.now();} public void setAttempts(int n){attempts=n;updatedAt=LocalDateTime.now();}
 public void setLastError(String e){lastError=e;updatedAt=LocalDateTime.now();}
}