package com.eatsleep.promotion.promotion.application.getrelatedpromotiontocreateusecase;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class TopRatedCommentResponse {
    private String idProduct;
    private double maxRate;
}
