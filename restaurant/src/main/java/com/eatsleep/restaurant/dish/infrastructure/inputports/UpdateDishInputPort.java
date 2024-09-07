package com.eatsleep.restaurant.dish.infrastructure.inputports;

import com.eatsleep.restaurant.dish.application.updatedishusecase.UpdateDishRequest;
import com.eatsleep.restaurant.dish.domain.Dish;
import java.util.Optional;

public interface UpdateDishInputPort {
    Optional<Dish> updateDish(String idDish, UpdateDishRequest dish);
}
