package com.eatsleep.promotion.promotion.infrastructure.inputports;

import com.eatsleep.promotion.promotion.application.updatepromotionusecase.UpdatePromotionRequest;
import com.eatsleep.promotion.promotion.domain.Promotion;
import java.util.Optional;

public interface UpdatePromotionInputPort {
    Optional<Promotion> updatePromotion(String idPromotion, UpdatePromotionRequest promotion);
}
