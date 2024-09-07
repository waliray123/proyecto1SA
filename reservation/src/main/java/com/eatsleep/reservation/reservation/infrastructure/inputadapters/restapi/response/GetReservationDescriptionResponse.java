package com.eatsleep.reservation.reservation.infrastructure.inputadapters.restapi.response;

import com.eatsleep.reservation.reservationdescription.domain.ReservationDescription;
import lombok.Value;

@Value
public class GetReservationDescriptionResponse {
    private String id;
    private String idProduct;
    private double unitPrice;
    private int quantity;
    private String idReservation;
    
    public GetReservationDescriptionResponse(ReservationDescription reservationDescription) {
        this.id = reservationDescription.getId().toString();
        this.idProduct = reservationDescription.getIdProduct().toString();
        this.unitPrice = reservationDescription.getUnitPrice();
        this.quantity = reservationDescription.getQuantity();
        this.idReservation = reservationDescription.getReservation().getId().toString();
    }
}
