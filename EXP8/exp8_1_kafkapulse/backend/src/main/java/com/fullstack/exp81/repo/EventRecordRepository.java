package com.fullstack.exp81.repo;
import com.fullstack.exp81.model.EventRecord;
import org.springframework.data.jpa.repository.JpaRepository;
public interface EventRecordRepository extends JpaRepository<EventRecord,String>{}