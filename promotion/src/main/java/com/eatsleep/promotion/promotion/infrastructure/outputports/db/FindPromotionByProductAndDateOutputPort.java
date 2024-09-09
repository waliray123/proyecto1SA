package com.eatsleep.promotion.promotion.infrastructure.outputports.db;

import com.eatsleep.promotion.promotion.domain.Promotion;
import java.time.LocalDate;
import java.util.Optional;

public interface FindPromotionByProductAndDateOutputPort {
    Optional<Promotion> findPromotionByProductAndDate(String idProduct, LocalDate date);
}
