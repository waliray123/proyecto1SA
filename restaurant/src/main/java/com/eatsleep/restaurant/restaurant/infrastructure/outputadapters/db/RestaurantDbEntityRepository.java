package com.eatsleep.restaurant.restaurant.infrastructure.outputadapters.db;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RestaurantDbEntityRepository extends JpaRepository<RestaurantDbEntity, String>{
    
}
