package com.eatsleep.restaurant.restaurant.application.createrestaurantusecase;

import com.eatsleep.restaurant.common.UseCase;
import com.eatsleep.restaurant.restaurant.domain.Restaurant;
import com.eatsleep.restaurant.restaurant.infrastructure.inputports.CreateRestaurantInputPort;
import com.eatsleep.restaurant.restaurant.infrastructure.outputadapters.db.RestaurantDbOutputAdapter;
import com.eatsleep.restaurant.restaurant.infrastructure.outputadapters.restapi.RestaurantRestApiOutputAdapter;
import jakarta.transaction.Transactional;
import java.util.UUID;
import org.springframework.beans.factory.annotation.Autowired;

@UseCase
@Transactional
public class CreateRestaurantUseCase implements CreateRestaurantInputPort{
    
    private RestaurantDbOutputAdapter restaurantOutputAdapter;
    private RestaurantRestApiOutputAdapter restaurantRestApiOutputAdapter;

    @Autowired
    public CreateRestaurantUseCase(RestaurantDbOutputAdapter restaurantOutputAdapter,RestaurantRestApiOutputAdapter restaurantRestApiOutputAdapter) {
        this.restaurantOutputAdapter = restaurantOutputAdapter;
        this.restaurantRestApiOutputAdapter = restaurantRestApiOutputAdapter;
    }

    @Override
    public Restaurant createRestaurant(CreateRestaurantRequest restaurantRequest) {        
        // Validar la información del restaurant
        if (restaurantRequest.getName() == null || restaurantRequest.getName().isEmpty()) {
            throw new IllegalArgumentException("El nombre del restaurant no puede estar vacío");
        }
        if (restaurantRequest.getLocation() == null || restaurantRequest.getLocation().isEmpty()) {
            throw new IllegalArgumentException("La ubicación del restaurant no puede estar vacía");
        }
        
        String hotelId = null;
        
        if(restaurantRequest.getIdHotel() != null && !restaurantRequest.getIdHotel().isEmpty()){
            if(!checkExistsHotel(restaurantRequest.getIdHotel())){
                throw new IllegalArgumentException("El identificador del hotel es invalido");
            }
            hotelId = restaurantRequest.getIdHotel();
        }
        
        
        
        // Crear el restaurant
        Restaurant restaurant = Restaurant.builder()
            .name(restaurantRequest.getName())
            .location(restaurantRequest.getLocation())
            .idHotel(hotelId != null ? UUID.fromString(hotelId) : null)
            .build();
        
        
        // Persistir el restaurant en la base de datos usando el Output Adapter
        Restaurant savedRestaurant = restaurantOutputAdapter.createRestaurant(restaurant);

        return savedRestaurant;
    }
    
    private boolean checkExistsHotel(String idHotel){
        return this.restaurantRestApiOutputAdapter.validateExistHotel(idHotel);
    }

}
