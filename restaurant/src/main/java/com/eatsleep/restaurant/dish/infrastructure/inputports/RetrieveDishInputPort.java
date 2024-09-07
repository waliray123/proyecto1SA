package com.eatsleep.restaurant.dish.infrastructure.inputports;

import com.eatsleep.restaurant.dish.domain.Dish;
import java.util.List;
import java.util.Optional;

public interface RetrieveDishInputPort {
    Optional<Dish> getDishById(String id);
    List<Dish> getAllDishs();
}
