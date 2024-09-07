package com.eatsleep.restaurant.restaurant.infrastructure.outputadapters.db;

import com.eatsleep.restaurant.common.OutputAdapter;
import com.eatsleep.restaurant.restaurant.domain.Restaurant;
import com.eatsleep.restaurant.restaurant.infrastructure.outputports.db.CreateRestaurantOutputPort;
import com.eatsleep.restaurant.restaurant.infrastructure.outputports.db.RetrieveRestaurantOutputPort;
import com.eatsleep.restaurant.restaurant.infrastructure.outputports.db.UpdateRestaurantOutputPort;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@OutputAdapter
public class RestaurantDbOutputAdapter implements CreateRestaurantOutputPort,UpdateRestaurantOutputPort,RetrieveRestaurantOutputPort{
    
    private RestaurantDbEntityRepository restaurantDbEntityRepository;

    public RestaurantDbOutputAdapter(RestaurantDbEntityRepository restaurantDbEntityRepository) {
        this.restaurantDbEntityRepository = restaurantDbEntityRepository;
    }

    @Override
    public Restaurant createRestaurant(Restaurant restaurant) {
        RestaurantDbEntity restaurantDbEntity = RestaurantDbEntity.from(restaurant);
        restaurantDbEntity = restaurantDbEntityRepository.save(restaurantDbEntity);
        return restaurantDbEntity.toDomainModel();
    }
    
    @Override
    public Optional<Restaurant> updateRestaurant(String id, Restaurant updatedRestaurant) {
        return restaurantDbEntityRepository.findById(id)
                .map(existingRestaurantEntity -> {                    
                    existingRestaurantEntity.setName(updatedRestaurant.getName());
                    existingRestaurantEntity.setLocation(updatedRestaurant.getLocation());
                    
                    RestaurantDbEntity savedRestaurantEntity = restaurantDbEntityRepository.save(existingRestaurantEntity);

                    return savedRestaurantEntity.toDomainModel();
                });
    }

    @Override
    public Optional<Restaurant> getRestaurantById(String id) {
        Optional<RestaurantDbEntity> restaurantEntity = restaurantDbEntityRepository.findById(id);
        return restaurantEntity
                .map(restaurantDbEntity -> restaurantDbEntity.toDomainModel());
    }

    @Override
    public List<Restaurant> getAllRestaurants() {
        return restaurantDbEntityRepository.findAll().stream()
                .map(restaurantDbEntity -> restaurantDbEntity.toDomainModel())
                .collect(Collectors.toList());
    }

}
