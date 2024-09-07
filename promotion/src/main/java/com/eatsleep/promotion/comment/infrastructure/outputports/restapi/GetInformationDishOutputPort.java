package com.eatsleep.promotion.comment.infrastructure.outputports.restapi;

import com.eatsleep.promotion.promotion.application.getrelatedpromotiontocreateusecase.DishPromotionResponse;

public interface GetInformationDishOutputPort {
    DishPromotionResponse getDishInformation(String idDish);
}
