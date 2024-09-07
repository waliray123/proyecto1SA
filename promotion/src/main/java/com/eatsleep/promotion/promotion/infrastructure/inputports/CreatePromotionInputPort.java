package com.eatsleep.promotion.promotion.infrastructure.inputports;

import com.eatsleep.promotion.promotion.application.createpromotionusecase.CreatePromotionRequest;
import com.eatsleep.promotion.promotion.domain.Promotion;

public interface CreatePromotionInputPort {
    
    Promotion createPromotion(CreatePromotionRequest promotion);

}
