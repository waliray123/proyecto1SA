package com.eatsleep.restaurant.restaurant.infrastructure.inputadapters.restapi.response;

import com.eatsleep.restaurant.restaurant.domain.Restaurant;
import lombok.Value;

@Value
public class CreateRestaurantResponse {

    private String id;
    private String name;
    private String location;
    private String idHotel;

    public CreateRestaurantResponse(Restaurant restaurant) {
        this.id = restaurant.getId().toString();
        this.name = restaurant.getName();
        this.location = restaurant.getLocation();
        this.idHotel = restaurant.getIdHotel() != null ? restaurant.getIdHotel().toString() : null;
    }
}
