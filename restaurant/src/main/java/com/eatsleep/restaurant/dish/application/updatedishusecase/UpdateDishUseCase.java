package com.eatsleep.restaurant.dish.application.updatedishusecase;

import com.eatsleep.restaurant.common.UseCase;
import com.eatsleep.restaurant.dish.domain.Dish;
import com.eatsleep.restaurant.restaurant.infrastructure.outputadapters.db.RestaurantDbOutputAdapter;
import com.eatsleep.restaurant.dish.infrastructure.inputports.UpdateDishInputPort;
import com.eatsleep.restaurant.dish.infrastructure.outputadapters.db.DishDbOutputAdapter;
import com.eatsleep.restaurant.restaurant.domain.Restaurant;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;

@UseCase
@Transactional
public class UpdateDishUseCase implements UpdateDishInputPort{

    
    private DishDbOutputAdapter dishOutputAdapter;
    private RestaurantDbOutputAdapter restaurantOutputAdapter;

    @Autowired
    public UpdateDishUseCase(DishDbOutputAdapter dishOutputAdapter, RestaurantDbOutputAdapter restaurantOutputAdapter) {
        this.dishOutputAdapter = dishOutputAdapter;
        this.restaurantOutputAdapter = restaurantOutputAdapter;
    }
    
    @Override
    public Optional<Dish> updateDish(String idDish, UpdateDishRequest dishRequest) {
        // Validar los datos del request
        if (dishRequest.getName() == null || dishRequest.getName().isEmpty()) {
            throw new IllegalArgumentException("El nombre del plato no puede estar vacío.");
        }
        if (dishRequest.getPrice() <= 0) {
            throw new IllegalArgumentException("El precio del plato debe ser mayor que cero.");
        }
        if (dishRequest.getIdRestaurant() == null) {
            throw new IllegalArgumentException("El ID del restaurante no puede estar vacío.");
        }

        // Buscar el restaurante
        Restaurant restaurant = restaurantOutputAdapter.getRestaurantById(dishRequest.getIdRestaurant().toString())
                .orElseThrow(() -> new EntityNotFoundException("El restaurante no existe, ingrese un ID válido."));

        // Buscar el plato existente en la base de datos
        Optional<Dish> existingDishOptional = dishOutputAdapter.getDishById(idDish);
        if (existingDishOptional.isEmpty()) {
            return Optional.empty();  // Si no se encuentra el plato, retornar Optional.empty()
        }

        // Actualizar los campos del plato existente
        Dish existingDish = existingDishOptional.get();
        existingDish.setName(dishRequest.getName());
        existingDish.setPrice(dishRequest.getPrice());
        existingDish.setDescription(dishRequest.getDescription());
        existingDish.setRestaurant(restaurant);

        // Guardar el plato actualizado en la base de datos
        Dish updatedDish = dishOutputAdapter.updateDish(idDish, existingDish)
                .orElseThrow(() -> new RuntimeException("Error al actualizar el plato"));

        // Convertir la entidad actualizada a un objeto de dominio y luego a una respuesta
        return Optional.of(updatedDish);
    }


}
