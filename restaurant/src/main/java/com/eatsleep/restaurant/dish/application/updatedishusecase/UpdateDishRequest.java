package com.eatsleep.restaurant.dish.application.updatedishusecase;

import com.eatsleep.restaurant.dish.application.createdishusecase.*;
import lombok.Value;

@Value
public class UpdateDishRequest {    
    private String name;
    private double price;
    private String description;
    private String idRestaurant;   
}
