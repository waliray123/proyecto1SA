package com.eatsleep.user.client.application.createclientusecase;

import com.eatsleep.user.employee.application.createemployeeusecase.*;
import lombok.Value;

@Value
public class CreateClientRequest {
    private String email;
    private String name;
    private String phone;
    private String type;
}
