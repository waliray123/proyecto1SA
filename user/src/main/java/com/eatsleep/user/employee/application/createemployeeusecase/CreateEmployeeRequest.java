package com.eatsleep.user.employee.application.createemployeeusecase;

import lombok.Value;

@Value
public class CreateEmployeeRequest {
    private String email;
    private String name;
    private String phone;
    private String type;
    private double weeklyPayment;
}
