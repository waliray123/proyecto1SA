package com.eatsleep.user.common.utils;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

public class PasswordUtils {

    private static final BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();

    public static String encryptPassword(String rawPassword) {
        return encoder.encode(rawPassword);
    }

    public static boolean matches(String rawPassword, String encodedPassword) {
        return encoder.matches(rawPassword, encodedPassword);
    }
}
