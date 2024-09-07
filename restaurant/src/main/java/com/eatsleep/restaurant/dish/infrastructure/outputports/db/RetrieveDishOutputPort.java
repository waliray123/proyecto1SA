package com.eatsleep.restaurant.dish.infrastructure.outputports.db;

import com.eatsleep.restaurant.dish.domain.Dish;
import java.util.List;
import java.util.Optional;

public interface RetrieveDishOutputPort {
    Optional<Dish> getDishById(String id);
    List<Dish> getAllDishs();
}
