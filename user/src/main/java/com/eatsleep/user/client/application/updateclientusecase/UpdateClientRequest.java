package com.eatsleep.user.client.application.updateclientusecase;

import lombok.Value;

@Value
public class UpdateClientRequest {
    private String email;
    private String name;
    private String password;
    private String phone;
    private String type;
}
