package com.eatsleep.user.client.infrastructure.inputports;

import com.eatsleep.user.client.application.updateclientusecase.UpdateClientRequest;
import com.eatsleep.user.client.domain.Client;
import java.util.Optional;

public interface UpdateClientInputPort {
    Optional<Client> updateClient(String idClient, UpdateClientRequest client);
}
