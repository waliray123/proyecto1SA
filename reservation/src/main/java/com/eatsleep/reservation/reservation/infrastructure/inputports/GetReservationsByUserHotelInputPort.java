package com.eatsleep.reservation.reservation.infrastructure.inputports;

import com.eatsleep.reservation.reservation.application.exceptions.ReservationException;
import com.eatsleep.reservation.reservation.domain.Reservation;
import java.util.List;


public interface GetReservationsByUserHotelInputPort {
    
    List<Reservation> getReservationByUserHotel(String idUser, String idHotel) throws ReservationException;

}
