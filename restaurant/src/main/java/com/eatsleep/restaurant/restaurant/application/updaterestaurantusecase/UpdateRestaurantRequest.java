package com.eatsleep.restaurant.restaurant.application.updaterestaurantusecase;

import lombok.Value;

@Value
public class UpdateRestaurantRequest {
    private String name;
    private String location;
    private String idHotel;
}
