package com.yasir.erp.minierp.common.generator;

import java.security.SecureRandom;
import java.time.Instant;

public class ShortIdGenerator {

    private static final char[] BASE62 = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz".
            toCharArray();
    private static final SecureRandom random = new SecureRandom();

    public static String generate(int length) {
        long timestamp = Instant.now().toEpochMilli();
        StringBuilder sb = new StringBuilder();


        while (timestamp > 0) {
            sb.append(BASE62[(int) (timestamp % 62)]);
            timestamp /= 62;
        }


        while (sb.length() < length) {
            sb.append(BASE62[random.nextInt(62)]);
        }

        return sb.substring(0, length); // fazlaysa kes
    }

    public static void main(String[] args) {

        for (int i = 0; i < 10; i++) {
            System.out.println(generate(10));
        }
    }
}
