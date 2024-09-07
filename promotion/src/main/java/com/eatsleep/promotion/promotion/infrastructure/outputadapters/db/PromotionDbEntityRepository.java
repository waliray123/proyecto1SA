package com.eatsleep.promotion.promotion.infrastructure.outputadapters.db;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PromotionDbEntityRepository extends JpaRepository<PromotionDbEntity, String>{
    
}
