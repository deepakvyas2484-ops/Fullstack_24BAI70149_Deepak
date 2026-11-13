package com.exp72.securetokens.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import javax.crypto.Cipher;
import javax.crypto.spec.GCMParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.nio.ByteBuffer;
import java.nio.charset.StandardCharsets;
import java.security.SecureRandom;
import java.util.Base64;

@Service
public class AesEncryptionService {
    private static final int NONCE_LENGTH = 12;
    private static final int TAG_LENGTH_BITS = 128;
    private final SecretKeySpec key;
    private final SecureRandom random = new SecureRandom();

    public AesEncryptionService(@Value("${app.crypto.aes-key-base64}") String encodedKey) {
        byte[] raw = Base64.getDecoder().decode(encodedKey);
        if (raw.length != 32) {
            throw new IllegalArgumentException("AES key must be exactly 32 bytes (256 bits).");
        }
        this.key = new SecretKeySpec(raw, "AES");
    }

    public String encrypt(String plaintext) throws Exception {
        byte[] nonce = new byte[NONCE_LENGTH];
        random.nextBytes(nonce);
        Cipher cipher = Cipher.getInstance("AES/GCM/NoPadding");
        cipher.init(Cipher.ENCRYPT_MODE, key, new GCMParameterSpec(TAG_LENGTH_BITS, nonce));
        byte[] ciphertext = cipher.doFinal(plaintext.getBytes(StandardCharsets.UTF_8));
        ByteBuffer combined = ByteBuffer.allocate(nonce.length + ciphertext.length);
        combined.put(nonce);
        combined.put(ciphertext);
        return Base64.getEncoder().encodeToString(combined.array());
    }

    public String decrypt(String encoded) throws Exception {
        byte[] combined = Base64.getDecoder().decode(encoded);
        if (combined.length <= NONCE_LENGTH) {
            throw new IllegalArgumentException("Encrypted payload is invalid.");
        }
        ByteBuffer buffer = ByteBuffer.wrap(combined);
        byte[] nonce = new byte[NONCE_LENGTH];
        buffer.get(nonce);
        byte[] ciphertext = new byte[buffer.remaining()];
        buffer.get(ciphertext);
        Cipher cipher = Cipher.getInstance("AES/GCM/NoPadding");
        cipher.init(Cipher.DECRYPT_MODE, key, new GCMParameterSpec(TAG_LENGTH_BITS, nonce));
        return new String(cipher.doFinal(ciphertext), StandardCharsets.UTF_8);
    }
}
