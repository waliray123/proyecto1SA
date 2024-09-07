package com.eatsleep.user.client.infrastructure.outputports.db;

import com.eatsleep.user.client.domain.Client;
import java.util.Optional;

public interface UpdateClientOutputPort {
    Optional<Client> updateClient(String id, Client updatedClient);
}
