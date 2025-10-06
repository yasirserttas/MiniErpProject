package com.yasir.erp.minierp.common.generator;

import java.security.SecureRandom;
import java.time.Instant;

public class UlidGenerator {

    private static final char[] ENCODING_CHARS = "0123456789ABCDEFGHJKMNPQRSTVWXYZ".toCharArray(); // Crockford's Base32 (I, L, O, U yok)
    private static final SecureRandom random = new SecureRandom();

    public static String generate() {
        long timestamp = Instant.now().toEpochMilli();
        byte[] randomness = new byte[10];
        random.nextBytes(randomness);

        return encodeTimestamp(timestamp) + encodeRandomness(randomness);
    }

    private static String encodeTimestamp(long timestamp) {
        char[] chars = new char[10];
        for (int i = 9; i >= 0; i--) {
            chars[i] = ENCODING_CHARS[(int)(timestamp & 31)];
            timestamp >>>= 5;
        }
        return new String(chars);
    }

    private static String encodeRandomness(byte[] data) {
        char[] chars = new char[16];
        int buffer = 0;
        int bitsLeft = 0;
        int charIndex = 0;

        for (byte b : data) {
            buffer = (buffer << 8) | (b & 0xff);
            bitsLeft += 8;
            while (bitsLeft >= 5) {
                bitsLeft -= 5;
                chars[charIndex++] = ENCODING_CHARS[(buffer >> bitsLeft) & 31];
            }
        }

        if (charIndex < 16) {
            chars[charIndex] = ENCODING_CHARS[(buffer << (5 - bitsLeft)) & 31];
        }

        return new String(chars);
    }

    public static void main(String[] args) {
        String ulid = UlidGenerator.generate();
        System.out.println("Generated ULID: " + ulid);
    }
}
