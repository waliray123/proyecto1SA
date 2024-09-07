package com.eatsleep.user.client.infrastructure.inputadapters.restapi.response;

import com.eatsleep.user.client.domain.Client;
import lombok.Value;

@Value
public class RetrieveClientResponse {
    private String id;
    private String email;
    private String password;
    private String name;
    private String phone;
    private String type;

    public RetrieveClientResponse(Client client) {
        this.id = client.getId().toString();
        this.email = client.getEmail();
        this.password = client.getPassword();
        this.name = client.getName();
        this.phone = client.getPhone();
        this.type = client.getType();
    }
}
