package com.eatsleep.user.client.application.checkexistclientusecase;

import com.eatsleep.user.common.UseCase;
import com.eatsleep.user.client.domain.Client;
import com.eatsleep.user.client.infrastructure.inputports.ExistClientInputPort;
import com.eatsleep.user.client.infrastructure.outputadapters.db.ClientDbOutputAdapter;
import jakarta.transaction.Transactional;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;

@UseCase
@Transactional
public class CheckExistClientUseCase implements ExistClientInputPort{
    
    private ClientDbOutputAdapter clientOutputAdapter;

    @Autowired
    public CheckExistClientUseCase(ClientDbOutputAdapter clientOutputAdapter) {
        this.clientOutputAdapter = clientOutputAdapter;
    }

    @Override
    public boolean checkExistClient(String idClient) {
        Optional<Client> clientOptional = clientOutputAdapter.getClientById(idClient);
        if(clientOptional.isPresent()){
            return true;
        }
        return false;
    }

}
