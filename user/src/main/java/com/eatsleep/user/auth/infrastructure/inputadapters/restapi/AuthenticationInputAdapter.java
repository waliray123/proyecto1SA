package com.eatsleep.user.auth.infrastructure.inputadapters.restapi;

import com.eatsleep.user.auth.application.loginusecase.AuthenticationRequest;
import com.eatsleep.user.auth.infrastructure.inputadapters.restapi.response.JwtResponse;
import com.eatsleep.user.auth.infrastructure.inputport.LoginUseCaseInputPort;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/v1/auth")
public class AuthenticationInputAdapter{

    private LoginUseCaseInputPort loginUseCaseInputPort;

    @Autowired
    public AuthenticationInputAdapter(LoginUseCaseInputPort loginUseCaseInputPort) {
        this.loginUseCaseInputPort = loginUseCaseInputPort;
    }

    @PostMapping("/login")
    public ResponseEntity<JwtResponse> authenticateAndGetToken(@RequestBody AuthenticationRequest authDTO) throws EntityNotFoundException {
        return ResponseEntity.ok(loginUseCaseInputPort.authenticateAndGetToken(authDTO));
    }

    @PostMapping("/logout")
    public ResponseEntity<String> logout() {
        return ResponseEntity.ok("xd");
    }

}
