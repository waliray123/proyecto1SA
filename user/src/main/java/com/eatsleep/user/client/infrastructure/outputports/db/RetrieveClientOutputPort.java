package com.eatsleep.user.client.infrastructure.outputports.db;

import com.eatsleep.user.client.domain.Client;
import java.util.List;
import java.util.Optional;

public interface RetrieveClientOutputPort {
    Optional<Client> getClientById(String id);
    Optional<Client> getClientByEmail(String email);
    List<Client> getAllClients();    
}
