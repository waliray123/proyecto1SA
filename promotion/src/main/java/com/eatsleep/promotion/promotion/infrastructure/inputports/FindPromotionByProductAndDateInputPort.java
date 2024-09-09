package com.eatsleep.promotion.promotion.infrastructure.inputports;

import com.eatsleep.promotion.promotion.domain.Promotion;
import java.time.LocalDate;
import java.util.Optional;

public interface FindPromotionByProductAndDateInputPort {
    Optional<Promotion> findPromotionByProductAndDate(String idProduct, LocalDate date);
}
