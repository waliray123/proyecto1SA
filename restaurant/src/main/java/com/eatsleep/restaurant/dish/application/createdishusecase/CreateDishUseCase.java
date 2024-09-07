package com.eatsleep.restaurant.dish.application.createdishusecase;

import com.eatsleep.restaurant.common.UseCase;
import com.eatsleep.restaurant.restaurant.infrastructure.outputadapters.db.RestaurantDbOutputAdapter;
import com.eatsleep.restaurant.dish.domain.Dish;
import com.eatsleep.restaurant.dish.infrastructure.inputports.CreateDishInputPort;
import com.eatsleep.restaurant.dish.infrastructure.outputadapters.db.DishDbOutputAdapter;
import com.eatsleep.restaurant.restaurant.domain.Restaurant;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;

@UseCase
@Transactional
public class CreateDishUseCase implements CreateDishInputPort{
    
    private DishDbOutputAdapter dishOutputAdapter;
    private RestaurantDbOutputAdapter restaurantOutputAdapter;

    @Autowired
    public CreateDishUseCase(DishDbOutputAdapter dishOutputAdapter, RestaurantDbOutputAdapter restaurantOutputAdapter) {
        this.dishOutputAdapter = dishOutputAdapter;
        this.restaurantOutputAdapter = restaurantOutputAdapter;
    }

    @Override
    public Dish createDish(CreateDishRequest dishRequest) {
         // Validar los datos del request
        if (dishRequest.getName() == null || dishRequest.getName().isEmpty()) {
            throw new IllegalArgumentException("El nombre del plato no puede estar vacío.");
        }
        if (dishRequest.getPrice() <= 0) {
            throw new IllegalArgumentException("El precio del plato debe ser mayor que cero.");
        }
        if (dishRequest.getDescription() == null) {
            throw new IllegalArgumentException("La descripcion del plato no puede estar vacío");
        }
        if (dishRequest.getIdRestaurant() == null) {
            throw new IllegalArgumentException("El ID del restaurante no puede estar vacío.");
        }

        // Buscar el restaurante
        Restaurant restaurant = restaurantOutputAdapter.getRestaurantById(dishRequest.getIdRestaurant().toString())
                .orElseThrow(() -> new EntityNotFoundException("El restaurante no existe, ingrese un ID válido."));

        // Crear la entidad DishDbEntity
        Dish dish = Dish.builder()
                .name(dishRequest.getName())
                .price(dishRequest.getPrice())
                .description(dishRequest.getDescription())
                .restaurant(restaurant)
                .build();

        // Guardar el plato en la base de datos
        Dish savedDish = dishOutputAdapter.createDish(dish);
        
        return savedDish;
    }

}
