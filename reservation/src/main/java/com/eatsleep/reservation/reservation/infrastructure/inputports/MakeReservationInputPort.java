package com.eatsleep.reservation.reservation.infrastructure.inputports;

import com.eatsleep.reservation.reservation.application.exceptions.ReservationException;
import com.eatsleep.reservation.reservation.application.makereservation.MakeReservationRequest;
import com.eatsleep.reservation.reservation.domain.Reservation;


public interface MakeReservationInputPort {
    
    Reservation makeReservation(MakeReservationRequest reservation, String idUser, String idHotel) throws ReservationException;

}
