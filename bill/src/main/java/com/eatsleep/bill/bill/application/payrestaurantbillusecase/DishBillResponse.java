package com.eatsleep.bill.bill.application.payrestaurantbillusecase;

import lombok.Value;

@Value
public class DishBillResponse {    
    private String id;
    private String name;
    private double price;
    private String description;
    private String idRestaurant;
}
