package com.exp72.securetokens.controller;

import com.exp72.securetokens.entity.EncryptedRecord;
import com.exp72.securetokens.repository.EncryptedRecordRepository;
import com.exp72.securetokens.service.AesEncryptionService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import java.util.Map;

@RestController
@RequestMapping("/api/crypto")
public class CryptoController {
    private final AesEncryptionService aes;
    private final EncryptedRecordRepository repository;

    public CryptoController(AesEncryptionService aes, EncryptedRecordRepository repository) {
        this.aes = aes;
        this.repository = repository;
    }

    @PostMapping("/encrypt")
    public Map<String, Object> encrypt(@RequestBody Map<String, String> body) throws Exception {
        String plaintext = body.get("plaintext");
        if (plaintext == null || plaintext.isBlank()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Enter text to encrypt.");
        }
        String encrypted = aes.encrypt(plaintext);
        EncryptedRecord saved = repository.save(new EncryptedRecord(encrypted));
        return Map.of("message", "Data encrypted and stored successfully.",
                "id", saved.getId(), "encryptedData", saved.getEncryptedData(),
                "createdAt", saved.getCreatedAt());
    }

    @GetMapping("/decrypt/{id}")
    public Map<String, Object> decrypt(@PathVariable Long id) throws Exception {
        EncryptedRecord record = repository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Record not found."));
        return Map.of("id", id, "decryptedData", aes.decrypt(record.getEncryptedData()),
                "encryptedData", record.getEncryptedData());
    }

    @GetMapping("/records")
    public Map<String, Object> records() {
        return Map.of("records", repository.findAll().stream().map(r ->
                Map.of("id", r.getId(), "encryptedData", r.getEncryptedData(),
                        "createdAt", r.getCreatedAt())).toList());
    }
}
