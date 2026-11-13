package com.fullstack.exp82.repo;
import com.fullstack.exp82.model.EventRecord;
import org.springframework.data.jpa.repository.JpaRepository;
public interface EventRecordRepository extends JpaRepository<EventRecord,String>{}