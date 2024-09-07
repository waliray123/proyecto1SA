package com.eatsleep.user.client.infrastructure.outputports.db;

import com.eatsleep.user.client.domain.Client;

public interface CreateClientOutputPort {
    Client createClient(Client client);
}
