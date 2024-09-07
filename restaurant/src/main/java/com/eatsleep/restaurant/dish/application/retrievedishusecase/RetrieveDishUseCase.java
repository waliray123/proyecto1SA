package com.eatsleep.restaurant.dish.application.retrievedishusecase;

import com.eatsleep.restaurant.common.UseCase;
import com.eatsleep.restaurant.dish.domain.Dish;
import com.eatsleep.restaurant.dish.infrastructure.inputports.RetrieveDishInputPort;
import com.eatsleep.restaurant.dish.infrastructure.outputadapters.db.DishDbOutputAdapter;
import jakarta.transaction.Transactional;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;

@UseCase
@Transactional
public class RetrieveDishUseCase implements RetrieveDishInputPort{
    
    private DishDbOutputAdapter dishOutputAdapter;

    @Autowired
    public RetrieveDishUseCase(DishDbOutputAdapter dishOutputAdapter) {
        this.dishOutputAdapter = dishOutputAdapter;
    }

    @Override
    public Optional<Dish> getDishById(String id) {
        // Recuperar la habitación por ID
        Optional<Dish> dishDbEntityOptional = dishOutputAdapter.getDishById(id);
        
        return dishDbEntityOptional;
    }

    @Override
    public List<Dish> getAllDishs() {
        // Recuperar todas las habitaciones
        List<Dish> dishDbEntities = dishOutputAdapter.getAllDishs();
        return dishDbEntities;
    }

}
