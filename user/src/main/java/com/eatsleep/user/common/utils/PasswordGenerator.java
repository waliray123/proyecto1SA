package com.eatsleep.user.common.utils;

import java.security.SecureRandom;
import java.util.Random;

public class PasswordGenerator {
    
    private final String CHAR_LOWER = "abcdefghijklmnopqrstuvwxyz";
    private final String CHAR_UPPER = CHAR_LOWER.toUpperCase();
    private final String NUMBER = "0123456789";
    private final String SPECIAL_CHARS = "!@#$%^&*()-_=+[]{}|;:,.<>?";

    private final String ALL_CHARS = CHAR_LOWER + CHAR_UPPER + NUMBER + SPECIAL_CHARS;

    private final Random random;

    public PasswordGenerator() {
        this(new SecureRandom());
    }

    public PasswordGenerator(Random random) {
        this.random = random;
    }

    public String generatePassword(int length) {
        if (length < 1) {
            throw new IllegalArgumentException("Password length must be at least 1");
        }

        StringBuilder sb = new StringBuilder(length);

        for (int i = 0; i < length; i++) {
            int randomIndex = random.nextInt(ALL_CHARS.length());
            sb.append(ALL_CHARS.charAt(randomIndex));
        }

        return sb.toString();
    } 
}
