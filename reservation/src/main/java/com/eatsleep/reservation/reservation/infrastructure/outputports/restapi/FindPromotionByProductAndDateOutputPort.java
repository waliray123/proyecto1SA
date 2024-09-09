package com.eatsleep.reservation.reservation.infrastructure.outputports.restapi;

import java.time.LocalDate;

public interface FindPromotionByProductAndDateOutputPort {
    Double findPromotionByProductAndDate(String idProduct, LocalDate date);
}
