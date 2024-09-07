package com.eatsleep.user.client.infrastructure.inputadapters.restapi;

import com.eatsleep.user.client.infrastructure.inputadapters.restapi.*;
import com.eatsleep.user.common.WebAdapter;
import com.eatsleep.user.client.application.createclientusecase.CreateClientRequest;
import com.eatsleep.user.client.application.exceptions.ClientAlreadyExistsException;
import com.eatsleep.user.client.infrastructure.inputadapters.restapi.response.CreateClientResponse;
import com.eatsleep.user.client.infrastructure.inputadapters.restapi.response.RetrieveClientResponse;
import com.eatsleep.user.client.application.updateclientusecase.UpdateClientRequest;
import com.eatsleep.user.client.domain.Client;
import com.eatsleep.user.client.infrastructure.inputadapters.restapi.response.UpdateClientResponse;
import com.eatsleep.user.client.infrastructure.inputports.CreateClientInputPort;
import com.eatsleep.user.client.infrastructure.inputports.ExistClientInputPort;
import com.eatsleep.user.client.infrastructure.inputports.RetrieveClientInputPort;
import com.eatsleep.user.client.infrastructure.inputports.UpdateClientInputPort;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/v1/clients")
@WebAdapter
public class ClientControllerAdapter {
    private CreateClientInputPort createClientInputPort;
    private UpdateClientInputPort updateClientInputPort;
    private RetrieveClientInputPort retrieveClientInputPort;
    private ExistClientInputPort existClientInputPort;

    
    @Autowired
    public ClientControllerAdapter(CreateClientInputPort createClientInputPort
            , UpdateClientInputPort updateClientInputPort
            , RetrieveClientInputPort retrieveClientInputPort
            , ExistClientInputPort existClientInputPort) {
        this.createClientInputPort = createClientInputPort;
        this.updateClientInputPort = updateClientInputPort;
        this.retrieveClientInputPort = retrieveClientInputPort;
        this.existClientInputPort = existClientInputPort;
    }
    
    @PostMapping("/save")
    public ResponseEntity<CreateClientResponse> createClientHotel(@RequestBody CreateClientRequest client) throws ClientAlreadyExistsException{
        Client createdClient = createClientInputPort.createClient(client);
        return new ResponseEntity<>(new CreateClientResponse(createdClient), HttpStatus.CREATED);
    }
    
    @GetMapping("/getclient/{id}")
    public ResponseEntity<RetrieveClientResponse> getClientById(@PathVariable String id) {
        Optional<Client> clientOptional = retrieveClientInputPort.getClientById(id);
        return clientOptional.map(client -> new ResponseEntity<>(new RetrieveClientResponse(client), HttpStatus.OK))
                            .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @GetMapping("/all")
    public ResponseEntity<List<RetrieveClientResponse>> getAllClients() {
        List<Client> clients = retrieveClientInputPort.getAllClients();
        List<RetrieveClientResponse> responseList = clients.stream()
                .map(RetrieveClientResponse::new)
                .toList();
        return new ResponseEntity<>(responseList, HttpStatus.OK);
    }
    
    @PutMapping("/updatehotel/{id}")
    public ResponseEntity<UpdateClientResponse> updateClientHotel(@PathVariable String id, @RequestBody UpdateClientRequest updatedClientDetails) {
        return updateClientInputPort.updateClient(id, updatedClientDetails)
                .map(client -> new ResponseEntity<>(new UpdateClientResponse(client), HttpStatus.OK))
                .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }
    
    @RequestMapping(method = RequestMethod.HEAD, path = "/exists/{idClient}")
    public ResponseEntity<Void> checkRestaurantExists(@PathVariable String idClient) {
        if (existClientInputPort.checkExistClient(idClient)) {
            return ResponseEntity.ok().build();
        }

        return ResponseEntity.notFound().build();
    }
    
    
}
