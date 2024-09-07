package com.eatsleep.promotion.promotion.infrastructure.inputports;

import com.eatsleep.promotion.promotion.domain.Promotion;
import java.util.List;
import java.util.Optional;

public interface RetrievePromotionInputPort {
    Optional<Promotion> getPromotionById(String id);
    List<Promotion> getAllPromotions();
}
