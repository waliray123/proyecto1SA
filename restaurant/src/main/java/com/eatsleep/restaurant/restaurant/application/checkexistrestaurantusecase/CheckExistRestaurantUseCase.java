package com.eatsleep.restaurant.restaurant.application.checkexistrestaurantusecase;

import com.eatsleep.restaurant.common.UseCase;
import com.eatsleep.restaurant.restaurant.domain.Restaurant;
import com.eatsleep.restaurant.restaurant.infrastructure.inputports.ExistRestaurantInputPort;
import com.eatsleep.restaurant.restaurant.infrastructure.outputadapters.db.RestaurantDbOutputAdapter;
import jakarta.transaction.Transactional;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;

@UseCase
@Transactional
public class CheckExistRestaurantUseCase implements ExistRestaurantInputPort{
    
    private RestaurantDbOutputAdapter restaurantOutputAdapter;

    @Autowired
    public CheckExistRestaurantUseCase(RestaurantDbOutputAdapter restaurantOutputAdapter) {
        this.restaurantOutputAdapter = restaurantOutputAdapter;
    }

    @Override
    public boolean checkExistRestaurant(String idRestaurant) {
        Optional<Restaurant> restaurantOptional = restaurantOutputAdapter.getRestaurantById(idRestaurant);
        if(restaurantOptional.isPresent()){
            return true;
        }
        return false;
    }

}
