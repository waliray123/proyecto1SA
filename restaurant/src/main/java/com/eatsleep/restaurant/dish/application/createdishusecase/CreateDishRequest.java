package com.eatsleep.restaurant.dish.application.createdishusecase;

import lombok.Value;

@Value
public class CreateDishRequest {    
    private String name;
    private double price;
    private String description;
    private String idRestaurant;   
}
