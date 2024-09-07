package com.eatsleep.restaurant.restaurant.application.createrestaurantusecase;

import lombok.Value;

@Value
public class CreateRestaurantRequest {    
    private String name;
    private String location;
    private String idHotel;
}
