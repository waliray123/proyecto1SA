package com.eatsleep.user.employee.infrastructure.inputadapters.restapi.response;

import com.eatsleep.user.employee.domain.Employee;
import lombok.Value;

@Value
public class CreateEmployeeResponse {
    private String id;
    private String email;
    private String password;
    private String name;
    private String phone;
    private String type;
    private double weeklyPayment;
    private String idLocation;

    public CreateEmployeeResponse(Employee employee) {
        this.id = employee.getId().toString();
        this.email = employee.getEmail();
        this.password = employee.getPassword();
        this.name = employee.getName();
        this.phone = employee.getPhone();
        this.type = employee.getType();
        this.weeklyPayment = employee.getWeeklyPayment();
        this.idLocation = employee.getIdLocation().toString();
    }


}
