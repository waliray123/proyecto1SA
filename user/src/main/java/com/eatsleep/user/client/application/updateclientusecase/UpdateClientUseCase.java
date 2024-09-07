package com.eatsleep.user.client.application.updateclientusecase;

import com.eatsleep.user.client.application.updateclientusecase.*;
import com.eatsleep.user.common.UseCase;
import com.eatsleep.user.client.domain.Client;
import com.eatsleep.user.client.infrastructure.inputports.UpdateClientInputPort;
import com.eatsleep.user.client.infrastructure.outputadapters.db.ClientDbOutputAdapter;
import jakarta.transaction.Transactional;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;

@UseCase
@Transactional
public class UpdateClientUseCase implements UpdateClientInputPort{
    
    private ClientDbOutputAdapter clientOutputAdapter;

    @Autowired
    public UpdateClientUseCase(ClientDbOutputAdapter clientOutputAdapter) {
        this.clientOutputAdapter = clientOutputAdapter;
    }

    @Override
    public Optional<Client> updateClient(String idClient, UpdateClientRequest clientRequest) {
        // Validar la información del client
        validateClientRequest(clientRequest);

        // Buscar el client existente en la base de datos
        Optional<Client> existingClientOptional = clientOutputAdapter.getClientById(idClient);
        if (existingClientOptional.isEmpty()) {
            return Optional.empty();  // Client no encontrado, retorno vacío
        }

        // Actualizar los campos del client existente
        Client existingClient = existingClientOptional.get();
        existingClient.setEmail(clientRequest.getEmail());
        existingClient.setPassword(clientRequest.getPassword());
        existingClient.setName(clientRequest.getName());
        existingClient.setPhone(clientRequest.getPhone());
        existingClient.setType(clientRequest.getType());

        // Guardar el client actualizado
        Optional<Client> updatedClientOptional = clientOutputAdapter.updateClient(idClient, existingClient);
        if(updatedClientOptional.isPresent()){
            updatedClientOptional.get().setPassword(clientRequest.getPassword());
        }
        
        // Crear y devolver la respuesta
        return updatedClientOptional;
    }
    
    private void validateClientRequest(UpdateClientRequest request) {
        if (request.getPassword() != null && request.getPassword().length() < 6) {
            throw new IllegalArgumentException("La contrasena tiene que se mayor a 6 digitos");
        }
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
