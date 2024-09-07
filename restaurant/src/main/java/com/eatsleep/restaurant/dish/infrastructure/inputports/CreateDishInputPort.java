package com.eatsleep.restaurant.dish.infrastructure.inputports;

import com.eatsleep.restaurant.dish.application.createdishusecase.CreateDishRequest;
import com.eatsleep.restaurant.dish.domain.Dish;

public interface CreateDishInputPort {
    
    Dish createDish(CreateDishRequest restaurant);

}
