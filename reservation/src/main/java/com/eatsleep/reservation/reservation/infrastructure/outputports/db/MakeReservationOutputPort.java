package com.eatsleep.reservation.reservation.infrastructure.outputports.db;

import com.eatsleep.reservation.reservation.domain.Reservation;

public interface MakeReservationOutputPort {
    Reservation makeReservation(Reservation reservation);
}
