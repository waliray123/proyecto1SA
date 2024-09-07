package com.eatsleep.restaurant.dish.application.checkexistdishusecase;

import com.eatsleep.restaurant.common.UseCase;
import com.eatsleep.restaurant.dish.domain.Dish;
import com.eatsleep.restaurant.dish.infrastructure.inputports.ExistDishInputPort;
import com.eatsleep.restaurant.dish.infrastructure.outputadapters.db.DishDbOutputAdapter;
import jakarta.transaction.Transactional;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;

@UseCase
@Transactional
public class CheckExistDishUseCase implements ExistDishInputPort{
    
    private DishDbOutputAdapter dishOutputAdapter;

    @Autowired
    public CheckExistDishUseCase(DishDbOutputAdapter dishOutputAdapter) {
        this.dishOutputAdapter = dishOutputAdapter;
    }

    @Override
    public boolean checkExistDish(String idDish) {
        Optional<Dish> restaurantOptional = dishOutputAdapter.getDishById(idDish);
        if(restaurantOptional.isPresent()){
            return true;
        }
        return false;
    }

}
