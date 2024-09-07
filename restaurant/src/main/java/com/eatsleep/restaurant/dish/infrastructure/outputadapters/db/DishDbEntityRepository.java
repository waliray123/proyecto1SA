package com.eatsleep.restaurant.dish.infrastructure.outputadapters.db;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DishDbEntityRepository extends JpaRepository<DishDbEntity, String>{
    
}
