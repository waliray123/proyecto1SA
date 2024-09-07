package com.eatsleep.bill.bill.application.payhotelbillusecase;

import lombok.Value;

@Value
public class GetReservationDescriptionResponse {
    private String id;
    private String idProduct;
    private double unitPrice;
    private int quantity;
    private String idReservation;
}
