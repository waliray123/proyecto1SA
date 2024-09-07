package com.eatsleep.restaurant.restaurant.infrastructure.outputports.db;

import com.eatsleep.restaurant.restaurant.domain.Restaurant;
import java.util.Optional;

public interface UpdateRestaurantOutputPort {
    Optional<Restaurant> updateRestaurant(String id, Restaurant updatedRestaurant);
}
