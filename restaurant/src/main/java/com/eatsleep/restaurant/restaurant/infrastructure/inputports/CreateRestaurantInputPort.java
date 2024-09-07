package com.eatsleep.restaurant.restaurant.infrastructure.inputports;

import com.eatsleep.restaurant.restaurant.application.createrestaurantusecase.CreateRestaurantRequest;
import com.eatsleep.restaurant.restaurant.domain.Restaurant;

public interface CreateRestaurantInputPort {
    
    Restaurant createRestaurant(CreateRestaurantRequest restaurant);

}
