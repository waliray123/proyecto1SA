package com.eatsleep.reservation.reservation.infrastructure.outputports.db;

import com.eatsleep.reservation.reservation.domain.Reservation;

public interface UpdateReservationOutputPort {
    Reservation updateReservation(Reservation reservation);
}
