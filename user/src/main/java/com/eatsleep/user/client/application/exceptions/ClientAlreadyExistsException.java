package com.eatsleep.user.client.application.exceptions;

import com.eatsleep.user.employee.application.exceptions.*;

public class ClientAlreadyExistsException extends Exception{

    public ClientAlreadyExistsException() {
    }

    public ClientAlreadyExistsException(String message) {
        super(message);
    }
    
}
