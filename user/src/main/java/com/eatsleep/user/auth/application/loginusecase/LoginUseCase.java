package com.eatsleep.user.auth.application.loginusecase;

import com.eatsleep.user.auth.infrastructure.inputadapters.restapi.response.JwtResponse;
import com.eatsleep.user.auth.infrastructure.inputport.LoginUseCaseInputPort;
import com.eatsleep.user.client.domain.Client;
import com.eatsleep.user.client.infrastructure.outputadapters.db.ClientDbOutputAdapter;
import com.eatsleep.user.common.UseCase;
import com.eatsleep.user.employee.domain.Employee;
import com.eatsleep.user.employee.infrastructure.outputadapters.db.EmployeeDbOutputAdapter;
import com.eatsleep.user.security.Role;
import com.eatsleep.user.security.jwt.JwtService;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;


@UseCase
@Transactional
public class LoginUseCase implements LoginUseCaseInputPort{
    
    private EmployeeDbOutputAdapter employeeOutputAdapter;
    private ClientDbOutputAdapter clientOutputAdapter;
    private AuthenticationManager authenticationManager;
    private JwtService jwtService;

    @Autowired
    public LoginUseCase(AuthenticationManager authenticationManager
            ,EmployeeDbOutputAdapter employeeOutputAdapter
            ,ClientDbOutputAdapter clientOutputAdapter
            ,JwtService jwtService) {
        this.authenticationManager = authenticationManager;
        this.employeeOutputAdapter = employeeOutputAdapter;
        this.clientOutputAdapter = clientOutputAdapter;
        this.jwtService = jwtService;
    }

    @Override
    public JwtResponse authenticateAndGetToken(AuthenticationRequest authRequest) throws EntityNotFoundException {
        Optional<Employee> employeeToAuth = employeeOutputAdapter.getEmployeeByEmail(authRequest.getUsername());
        Role roleToAuth = Role.CUSTOMER;
        if(employeeToAuth.isPresent()){
            roleToAuth = employeeToAuth.get().getRole();
        }else{
            Client clientToAuth = clientOutputAdapter.getClientByEmail(authRequest.getUsername())
                .orElseThrow(() -> new EntityNotFoundException(String.format("Usuario no encontrado", authRequest.getUsername())));
            roleToAuth = clientToAuth.getRole();
        }

        UsernamePasswordAuthenticationToken authData
                = new UsernamePasswordAuthenticationToken(authRequest.getUsername(), authRequest.getPassword());

        try {
            Authentication authentication = authenticationManager.authenticate(authData);
            if (authentication.isAuthenticated()) {
                jwtService.updateTokenExpiration(authRequest.getUsername());
                return new JwtResponse(jwtService.generateToken(authRequest.getUsername(), roleToAuth), roleToAuth);
            }
        } catch (AuthenticationException ex) {
            System.out.println(ex);
            System.out.println("Error autenticando");
        }
        throw new UsernameNotFoundException("Invalid user request");
    }

}
