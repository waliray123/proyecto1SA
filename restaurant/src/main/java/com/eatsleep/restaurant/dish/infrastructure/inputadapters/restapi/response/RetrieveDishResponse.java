package com.eatsleep.restaurant.dish.infrastructure.inputadapters.restapi.response;

import com.eatsleep.restaurant.dish.application.createdishusecase.*;
import com.eatsleep.restaurant.dish.domain.Dish;
import lombok.Value;

@Value
public class RetrieveDishResponse {

    private String id;
    private String name;
    private double price;
    private String description;
    private String idRestaurant;    

    public RetrieveDishResponse(Dish dish) {
        this.id = dish.getId().toString();
        this.name = dish.getName();
        this.price = dish.getPrice();
        this.description = dish.getDescription();
        this.idRestaurant = dish.getRestaurant().getId().toString();
    }
    
    

    
}
