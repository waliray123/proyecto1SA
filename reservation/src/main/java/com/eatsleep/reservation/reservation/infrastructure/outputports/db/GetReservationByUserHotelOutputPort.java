package com.eatsleep.reservation.reservation.infrastructure.outputports.db;

import com.eatsleep.reservation.reservation.domain.Reservation;
import java.util.List;

public interface GetReservationByUserHotelOutputPort {    
    List<Reservation> findReservationByUserHotel(String idUser, String idHotel);
}
