package com.eatsleep.promotion.promotion.infrastructure.outputports.db;

import com.eatsleep.promotion.promotion.domain.Promotion;
import java.util.Optional;

public interface UpdatePromotionOutputPort {
    Optional<Promotion> updatePromotion(String id, Promotion updatedPromotion);
}
