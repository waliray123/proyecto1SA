package com.eatsleep.promotion.promotion.infrastructure.outputports.db;

import com.eatsleep.promotion.promotion.domain.Promotion;

public interface CreatePromotionOutputPort {
    Promotion createPromotion(Promotion promotion);
}
