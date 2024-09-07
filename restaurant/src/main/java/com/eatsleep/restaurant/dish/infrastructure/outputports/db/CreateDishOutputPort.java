package com.eatsleep.restaurant.dish.infrastructure.outputports.db;

import com.eatsleep.restaurant.dish.domain.Dish;

public interface CreateDishOutputPort {
    Dish createDish(Dish dish);
}
