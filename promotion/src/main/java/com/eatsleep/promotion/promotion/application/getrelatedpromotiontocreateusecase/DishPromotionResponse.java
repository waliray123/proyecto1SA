package com.eatsleep.promotion.promotion.application.getrelatedpromotiontocreateusecase;

import lombok.Value;

@Value
public class DishPromotionResponse {    
    private String id;
    private String name;
    private double price;
    private String description;
    private String idRestaurant;
}
