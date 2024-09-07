package com.eatsleep.restaurant.restaurant.application.retrieverestaurantusecase;

import com.eatsleep.restaurant.restaurant.infrastructure.inputadapters.restapi.response.RetrieveRestaurantResponse;
import com.eatsleep.restaurant.common.UseCase;
import com.eatsleep.restaurant.restaurant.domain.Restaurant;
import com.eatsleep.restaurant.restaurant.infrastructure.inputports.RetrieveRestaurantInputPort;
import com.eatsleep.restaurant.restaurant.infrastructure.outputadapters.db.RestaurantDbEntity;
import com.eatsleep.restaurant.restaurant.infrastructure.outputadapters.db.RestaurantDbOutputAdapter;
import jakarta.transaction.Transactional;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;

@UseCase
@Transactional
public class RetrieveRestaurantUseCase implements RetrieveRestaurantInputPort{
    
    private RestaurantDbOutputAdapter restaurantOutputAdapter;

    @Autowired
    public RetrieveRestaurantUseCase(RestaurantDbOutputAdapter restaurantOutputAdapter) {
        this.restaurantOutputAdapter = restaurantOutputAdapter;
    }

    @Override
    public Optional<Restaurant> getRestaurantById(String id) {
        // Recuperar el restaurant por ID
        Optional<Restaurant> restaurantEntityOptional = restaurantOutputAdapter.getRestaurantById(id);

        return restaurantEntityOptional;
    }

    @Override
    public List<Restaurant> getAllRestaurants() {
        // Recuperar todos los restaurantes
        return restaurantOutputAdapter.getAllRestaurants();
    }

}
