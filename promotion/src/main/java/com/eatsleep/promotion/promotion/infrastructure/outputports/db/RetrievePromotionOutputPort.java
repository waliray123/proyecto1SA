package com.eatsleep.promotion.promotion.infrastructure.outputports.db;

import com.eatsleep.promotion.promotion.domain.Promotion;
import java.util.List;
import java.util.Optional;

public interface RetrievePromotionOutputPort {
    Optional<Promotion> getPromotionById(String id);
    List<Promotion> getAllPromotions();    
}
