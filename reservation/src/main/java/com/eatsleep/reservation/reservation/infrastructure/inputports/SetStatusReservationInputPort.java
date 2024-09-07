package com.eatsleep.reservation.reservation.infrastructure.inputports;

public interface SetStatusReservationInputPort {
    boolean setStatusReservation(String idReservation, String status);
}
