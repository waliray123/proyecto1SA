package com.eatsleep.restaurant.restaurant.application.updaterestaurantusecase;

import com.eatsleep.restaurant.restaurant.infrastructure.inputadapters.restapi.response.UpdateRestaurantResponse;
import com.eatsleep.restaurant.common.UseCase;
import com.eatsleep.restaurant.restaurant.domain.Restaurant;
import com.eatsleep.restaurant.restaurant.infrastructure.inputports.UpdateRestaurantInputPort;
import com.eatsleep.restaurant.restaurant.infrastructure.outputadapters.db.RestaurantDbEntity;
import com.eatsleep.restaurant.restaurant.infrastructure.outputadapters.db.RestaurantDbOutputAdapter;
import jakarta.transaction.Transactional;
import java.util.Optional;
import java.util.UUID;
import org.springframework.beans.factory.annotation.Autowired;

@UseCase
@Transactional
public class UpdateRestaurantUseCase implements UpdateRestaurantInputPort{
    
    private RestaurantDbOutputAdapter restaurantOutputAdapter;

    @Autowired
    public UpdateRestaurantUseCase(RestaurantDbOutputAdapter restaurantOutputAdapter) {
        this.restaurantOutputAdapter = restaurantOutputAdapter;
    }

    @Override
    public Optional<Restaurant> updateRestaurant(String idRestaurant, UpdateRestaurantRequest restaurantRequest) {
         // Validar la información del restaurant
        if (restaurantRequest.getName() == null || restaurantRequest.getName().isEmpty()) {
            throw new IllegalArgumentException("El nombre del restaurant no puede estar vacío");
        }
        if (restaurantRequest.getLocation() == null || restaurantRequest.getLocation().isEmpty()) {
            throw new IllegalArgumentException("La ubicación del restaurant no puede estar vacía");
        }

        // Buscar el restaurant existente en la base de datos
        Optional<Restaurant> existingRestaurantOptional = restaurantOutputAdapter.getRestaurantById(idRestaurant);
        if (existingRestaurantOptional.isEmpty()) {
            return Optional.empty();  // Restaurant no encontrado, retorno vacío
        }
        
        String hotelId = null;
        if(restaurantRequest.getIdHotel() != null){
            //existingRestaurantOptional.get().getIdHotel().toString();
        }

        // Actualizar los campos del restaurant existente
        Restaurant existingRestaurant = existingRestaurantOptional.get();
        existingRestaurant.setName(restaurantRequest.getName());
        existingRestaurant.setLocation(restaurantRequest.getLocation());
        existingRestaurant.setIdHotel(hotelId != null ? UUID.fromString(hotelId) : null);

        // Guardar el restaurant actualizado
        Optional<Restaurant> updatedRestaurantOptional = restaurantOutputAdapter.updateRestaurant(idRestaurant, existingRestaurant);
        
        // Crear y devolver la respuesta
        return updatedRestaurantOptional;
    }



}
