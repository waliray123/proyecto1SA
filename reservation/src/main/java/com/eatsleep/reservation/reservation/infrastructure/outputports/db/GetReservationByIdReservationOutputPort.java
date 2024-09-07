package com.eatsleep.reservation.reservation.infrastructure.outputports.db;

import com.eatsleep.reservation.reservation.domain.Reservation;
import java.util.Optional;

public interface GetReservationByIdReservationOutputPort {    
    Optional<Reservation> findReservationByIdReservation(String idReservation);
}
