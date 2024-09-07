package com.eatsleep.user.client.application.retrieveclientusecase;

import com.eatsleep.user.client.application.retrieveclientusecase.*;
import com.eatsleep.user.common.UseCase;
import com.eatsleep.user.client.domain.Client;
import com.eatsleep.user.client.infrastructure.inputports.RetrieveClientInputPort;
import com.eatsleep.user.client.infrastructure.outputadapters.db.ClientDbOutputAdapter;
import jakarta.transaction.Transactional;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;

@UseCase
@Transactional
public class RetrieveClientUseCase implements RetrieveClientInputPort{
    
    private ClientDbOutputAdapter clientOutputAdapter;

    @Autowired
    public RetrieveClientUseCase(ClientDbOutputAdapter clientOutputAdapter) {
        this.clientOutputAdapter = clientOutputAdapter;
    }

    @Override
    public Optional<Client> getClientById(String id) {
        // Recuperar el client por ID
        Optional<Client> clientEntityOptional = clientOutputAdapter.getClientById(id);

        return clientEntityOptional;
    }

    @Override
    public List<Client> getAllClients() {
        // Recuperar todos los clientes
        return clientOutputAdapter.getAllClients();
    }

    @Override
    public Optional<Client> getClientByEmail(String email) {
        return clientOutputAdapter.getClientByEmail(email);
    }

}
