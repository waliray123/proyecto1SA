package com.eatsleep.promotion.promotion.application.getrelatedpromotiontocreateusecase;

import lombok.Value;

@Value
public class RoomPromotionResponse {
    private String id;
    private int number;
    private double unitPrice;
    private boolean occupied;
    private String idHotel;
}
