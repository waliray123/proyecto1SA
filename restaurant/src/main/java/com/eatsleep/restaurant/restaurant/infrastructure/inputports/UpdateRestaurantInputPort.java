package com.eatsleep.restaurant.restaurant.infrastructure.inputports;

import com.eatsleep.restaurant.restaurant.application.updaterestaurantusecase.UpdateRestaurantRequest;
import com.eatsleep.restaurant.restaurant.domain.Restaurant;
import java.util.Optional;

public interface UpdateRestaurantInputPort {
    Optional<Restaurant> updateRestaurant(String idRestaurant, UpdateRestaurantRequest restaurant);
}
