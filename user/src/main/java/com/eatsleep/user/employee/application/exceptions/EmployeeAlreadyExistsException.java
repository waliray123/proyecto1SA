package com.eatsleep.user.employee.application.exceptions;

public class EmployeeAlreadyExistsException extends Exception{

    public EmployeeAlreadyExistsException() {
    }

    public EmployeeAlreadyExistsException(String message) {
        super(message);
    }
    
}
