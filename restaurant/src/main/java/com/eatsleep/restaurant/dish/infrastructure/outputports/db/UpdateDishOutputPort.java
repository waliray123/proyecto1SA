package com.eatsleep.restaurant.dish.infrastructure.outputports.db;

import com.eatsleep.restaurant.dish.domain.Dish;
import java.util.Optional;

public interface UpdateDishOutputPort {
    Optional<Dish> updateDish(String id, Dish updatedDish);
}
