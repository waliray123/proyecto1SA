package com.eatsleep.user.client.infrastructure.outputadapters.db;

import com.eatsleep.user.common.OutputAdapter;
import com.eatsleep.user.client.domain.Client;
import com.eatsleep.user.client.infrastructure.outputports.db.CreateClientOutputPort;
import com.eatsleep.user.client.infrastructure.outputports.db.RetrieveClientOutputPort;
import com.eatsleep.user.client.infrastructure.outputports.db.UpdateClientOutputPort;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@OutputAdapter
public class ClientDbOutputAdapter implements CreateClientOutputPort,UpdateClientOutputPort,RetrieveClientOutputPort{
    
    private ClientDbEntityRepository clientDbEntityRepository;

    public ClientDbOutputAdapter(ClientDbEntityRepository clientDbEntityRepository) {
        this.clientDbEntityRepository = clientDbEntityRepository;
    }

    @Override
    public Client createClient(Client client) {
        ClientDbEntity clientDbEntity = ClientDbEntity.from(client);
        clientDbEntity = clientDbEntityRepository.save(clientDbEntity);
        return clientDbEntity.toDomainModel();
    }
    
    @Override
    public Optional<Client> updateClient(String id, Client updatedClient) {
        Optional<ClientDbEntity> existingClientOpt = clientDbEntityRepository.findById(id);

        if (existingClientOpt.isPresent()) {
            ClientDbEntity updatedEntityClient = ClientDbEntity.from(updatedClient);
            
            ClientDbEntity savedClient = clientDbEntityRepository.save(updatedEntityClient);

            return Optional.of(savedClient.toDomainModel());
        } else {
            return Optional.empty();
        }
    }

    @Override
    public Optional<Client> getClientById(String id) {
        Optional<ClientDbEntity> clientEntity = clientDbEntityRepository.findById(id);
        return clientEntity
                .map(clientDbEntity -> clientDbEntity.toDomainModel());
    }

    @Override
    public List<Client> getAllClients() {
        return clientDbEntityRepository.findAll().stream()
                .map(clientDbEntity -> clientDbEntity.toDomainModel())
                .collect(Collectors.toList());
    }

    @Override
    public Optional<Client> getClientByEmail(String email) {
        Optional<ClientDbEntity> clientEntity = clientDbEntityRepository.findByEmail(email);
        return clientEntity
                .map(clientDbEntity -> clientDbEntity.toDomainModel());
    }

}
