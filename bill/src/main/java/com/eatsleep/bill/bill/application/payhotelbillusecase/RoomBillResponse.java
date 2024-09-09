package com.eatsleep.bill.bill.application.payhotelbillusecase;

import lombok.Value;

@Value
public class RoomBillResponse {
    private String id;
    private int number;
    private double unitPrice;
    private boolean status;
    private String idHotel;
}
