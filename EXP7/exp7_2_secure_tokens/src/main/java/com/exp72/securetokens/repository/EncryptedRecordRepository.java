package com.exp72.securetokens.repository;

import com.exp72.securetokens.entity.EncryptedRecord;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EncryptedRecordRepository extends JpaRepository<EncryptedRecord, Long> {}
