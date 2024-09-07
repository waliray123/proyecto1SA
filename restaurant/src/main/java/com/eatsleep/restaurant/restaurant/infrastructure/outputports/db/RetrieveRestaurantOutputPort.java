package com.eatsleep.restaurant.restaurant.infrastructure.outputports.db;

import com.eatsleep.restaurant.restaurant.domain.Restaurant;
import java.util.List;
import java.util.Optional;

public interface RetrieveRestaurantOutputPort {
    Optional<Restaurant> getRestaurantById(String id);
    List<Restaurant> getAllRestaurants();    
}
