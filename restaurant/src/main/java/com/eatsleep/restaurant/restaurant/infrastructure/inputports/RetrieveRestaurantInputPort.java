package com.eatsleep.restaurant.restaurant.infrastructure.inputports;

import com.eatsleep.restaurant.restaurant.domain.Restaurant;
import java.util.List;
import java.util.Optional;

public interface RetrieveRestaurantInputPort {
    Optional<Restaurant> getRestaurantById(String id);
    List<Restaurant> getAllRestaurants();
}
