package com.eatsleep.restaurant.restaurant.infrastructure.outputports.db;

import com.eatsleep.restaurant.restaurant.domain.Restaurant;

public interface CreateRestaurantOutputPort {
    Restaurant createRestaurant(Restaurant restaurant);
}
