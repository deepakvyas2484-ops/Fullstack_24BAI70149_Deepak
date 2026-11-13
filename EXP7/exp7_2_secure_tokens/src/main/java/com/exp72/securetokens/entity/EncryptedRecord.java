package com.exp72.securetokens.entity;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "encrypted_records")
public class EncryptedRecord {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 10000)
    private String encryptedData;

    @Column(nullable = false)
    private LocalDateTime createdAt;

    protected EncryptedRecord() {}

    public EncryptedRecord(String encryptedData) {
        this.encryptedData = encryptedData;
        this.createdAt = LocalDateTime.now();
    }

    public Long getId() { return id; }
    public String getEncryptedData() { return encryptedData; }
    public LocalDateTime getCreatedAt() { return createdAt; }
}
