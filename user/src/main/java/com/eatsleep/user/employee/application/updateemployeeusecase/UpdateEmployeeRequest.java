package com.eatsleep.user.employee.application.updateemployeeusecase;

import lombok.Value;

@Value
public class UpdateEmployeeRequest {
    private String email;
    private String name;
    private String password;
    private String phone;
    private String type;
    private double weeklyPayment;
}
