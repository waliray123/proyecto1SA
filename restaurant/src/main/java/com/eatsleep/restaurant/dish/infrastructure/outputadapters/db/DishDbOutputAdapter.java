package com.eatsleep.restaurant.dish.infrastructure.outputadapters.db;

import com.eatsleep.restaurant.common.OutputAdapter;
import com.eatsleep.restaurant.dish.domain.Dish;
import com.eatsleep.restaurant.dish.infrastructure.outputports.db.CreateDishOutputPort;
import com.eatsleep.restaurant.dish.infrastructure.outputports.db.RetrieveDishOutputPort;
import com.eatsleep.restaurant.dish.infrastructure.outputports.db.UpdateDishOutputPort;
import com.eatsleep.restaurant.restaurant.domain.Restaurant;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@OutputAdapter
public class DishDbOutputAdapter implements CreateDishOutputPort,UpdateDishOutputPort,RetrieveDishOutputPort{
    
    private DishDbEntityRepository dishDbEntityRepository;

    public DishDbOutputAdapter(DishDbEntityRepository dishDbEntityRepository) {
        this.dishDbEntityRepository = dishDbEntityRepository;
    }

    @Override
    public Dish createDish(Dish dish) {
        DishDbEntity dishEntity = DishDbEntity.fromDomainModel(dish);
        return dishDbEntityRepository.save(dishEntity).toDomainModel();
    }

    @Override
    public Optional<Dish> updateDish(String id, Dish updatedDish) {
        if (dishDbEntityRepository.existsById(id)) {
            DishDbEntity updatedDishEntity = DishDbEntity.fromDomainModel(updatedDish);
            DishDbEntity savedEntity = dishDbEntityRepository.save(updatedDishEntity);
            Dish savedDish = savedEntity.toDomainModel();
            return Optional.of(savedDish);
        }
        return Optional.empty();
    }

    @Override
    public Optional<Dish> getDishById(String id) {
        Optional<DishDbEntity> dishEntity = dishDbEntityRepository.findById(id);
        return dishEntity
                .map(dishDbEntity -> dishDbEntity.toDomainModel());
    }
    
    @Override
    public List<Dish> getAllDishs() {
        return dishDbEntityRepository.findAll().stream()
                .map(dishDbEntity -> dishDbEntity.toDomainModel())
                .collect(Collectors.toList());
    }
    

}
