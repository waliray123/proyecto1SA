package com.eatsleep.user.auth.application.loginusecase;

import lombok.Value;

@Value
public class AuthenticationRequest {
    private final String username;
    private final String password;
}
