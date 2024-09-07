package com.eatsleep.user.client.infrastructure.inputports;

import com.eatsleep.user.client.application.createclientusecase.CreateClientRequest;
import com.eatsleep.user.client.application.exceptions.ClientAlreadyExistsException;
import com.eatsleep.user.client.domain.Client;

public interface CreateClientInputPort {
    
    Client createClient(CreateClientRequest client) throws ClientAlreadyExistsException;

}
