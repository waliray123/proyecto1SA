package com.eatsleep.promotion.promotion.application.updatepromotionusecase;

import java.time.LocalDate;
import lombok.Value;

@Value
public class UpdatePromotionRequest {    
    private LocalDate startDate;
    private LocalDate endDate;
    private String type;
    private String description;
    private String typeClient;
    private String idProduct;
    private double valuePromotion;
}
