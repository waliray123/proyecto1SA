package com.eatsleep.bill.bill.infrastructure.outputports.restapi;

import java.time.LocalDate;

public interface FindPromotionByProductAndDateOutputPort {
    Double findPromotionByProductAndDate(String idProduct, LocalDate date);
}
