package com.eatsleep.reservation.reservation.infrastructure.inputports;

import com.eatsleep.reservation.reservation.application.exceptions.ReservationException;
import com.eatsleep.reservation.reservation.domain.Reservation;
import java.util.Optional;


public interface GetReservationByIdReservationInputPort {
    
    Optional<Reservation> getReservationByIdReservation(String idReservation) throws ReservationException;

}
