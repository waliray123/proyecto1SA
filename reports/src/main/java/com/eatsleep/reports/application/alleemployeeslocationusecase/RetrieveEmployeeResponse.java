package com.eatsleep.reports.application.alleemployeeslocationusecase;

import lombok.Value;

@Value
public class RetrieveEmployeeResponse {
    private String id;
    private String email;
    private String password;
    private String name;
    private String phone;
    private String type;
    private double weeklyPayment;
    private String idLocation;

}
