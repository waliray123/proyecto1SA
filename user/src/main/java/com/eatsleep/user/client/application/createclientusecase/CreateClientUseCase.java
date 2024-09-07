package com.eatsleep.user.client.application.createclientusecase;

import com.eatsleep.user.client.application.createclientusecase.*;
import com.eatsleep.user.common.UseCase;
import com.eatsleep.user.common.utils.PasswordGenerator;
import com.eatsleep.user.client.application.exceptions.ClientAlreadyExistsException;
import com.eatsleep.user.client.domain.Client;
import com.eatsleep.user.client.infrastructure.inputports.CreateClientInputPort;
import com.eatsleep.user.client.infrastructure.outputadapters.db.ClientDbOutputAdapter;
import jakarta.transaction.Transactional;
import java.util.UUID;
import org.springframework.beans.factory.annotation.Autowired;

@UseCase
@Transactional
public class CreateClientUseCase implements CreateClientInputPort{
    
    private ClientDbOutputAdapter clientOutputAdapter;

    @Autowired
    public CreateClientUseCase(ClientDbOutputAdapter clientOutputAdapter) {
        this.clientOutputAdapter = clientOutputAdapter;
    }

    @Override
    public Client createClient(CreateClientRequest clientRequest) throws ClientAlreadyExistsException{
        // Validar la información del empleado
        validateClientRequest(clientRequest);
        
        // Validar que no exista un empleado con el mismo correo
        if (this.clientOutputAdapter.getClientByEmail(clientRequest.getEmail()).isPresent()) {
            throw new ClientAlreadyExistsException("Un empleado con email: " + clientRequest.getEmail() + " ya existe");
        }

        //Generar una password
        PasswordGenerator passwordGenerator = new PasswordGenerator();
        String password = passwordGenerator.generatePassword(8);

        // Crear el empleado
        Client client = Client.builder()
                .id(UUID.randomUUID())
                .email(clientRequest.getEmail())
                .password(password)
                .name(clientRequest.getName())
                .phone(clientRequest.getPhone())
                .type(clientRequest.getType())
                .build();

        // Persistir el empleado en la base de datos usando el Output Adapter
        Client savedClient = clientOutputAdapter.createClient(client);
        savedClient.setPassword(password);

        return savedClient;
    }
    
    private void validateClientRequest(CreateClientRequest request) {
        if (request.getEmail() == null || request.getEmail().isEmpty()) {
            throw new IllegalArgumentException("Correo requerido");
        }
        if (!isValidEmail(request.getEmail())) {
            throw new IllegalArgumentException("Formato de email incorrecto");
        }
        if (request.getName() == null || request.getName().isEmpty()) {
            throw new IllegalArgumentException("El nombre es requerido");
        }
        if (request.getPhone() != null && !request.getPhone().matches("\\+?[0-9]*")) {
            throw new IllegalArgumentException("El numero de telefono solo puede tener digitos y tiene que empezar con '+'");
        }
        if (request.getType() == null || request.getType().isEmpty()) {
            throw new IllegalArgumentException("El tipo de cliente es requerido");
        }
    }
    
    private boolean isValidEmail(String email) {
        // Simple email validation (can be improved)
        String emailRegex = "^[a-zA-Z0-9_+&*-]+(?:\\.[a-zA-Z0-9_+&*-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,7}$";
        return email.matches(emailRegex);
    }

}
