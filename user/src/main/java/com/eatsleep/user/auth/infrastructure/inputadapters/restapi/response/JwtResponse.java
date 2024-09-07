package com.eatsleep.user.auth.infrastructure.inputadapters.restapi.response;

import com.eatsleep.user.security.Role;
import lombok.Value;

@Value
public class JwtResponse {
    private final String token;

    private final Role role;

}
