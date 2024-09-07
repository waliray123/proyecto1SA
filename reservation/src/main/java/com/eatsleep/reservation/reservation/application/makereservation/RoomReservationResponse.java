package com.eatsleep.reservation.reservation.application.makereservation;

import lombok.Value;

@Value
public class RoomReservationResponse {
    private String id;
    private int number;
    private double unitPrice;
    private boolean occupied;
    private String idHotel;
}
