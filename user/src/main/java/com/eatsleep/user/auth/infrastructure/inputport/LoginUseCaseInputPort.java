package com.eatsleep.user.auth.infrastructure.inputport;

import com.eatsleep.user.auth.application.loginusecase.AuthenticationRequest;
import com.eatsleep.user.auth.infrastructure.inputadapters.restapi.response.JwtResponse;
import jakarta.persistence.EntityNotFoundException;

public interface LoginUseCaseInputPort {
    JwtResponse authenticateAndGetToken(AuthenticationRequest authRequest) throws EntityNotFoundException;
}