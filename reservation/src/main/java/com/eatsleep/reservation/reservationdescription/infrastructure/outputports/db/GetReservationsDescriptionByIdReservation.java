package com.eatsleep.reservation.reservationdescription.infrastructure.outputports.db;

import com.eatsleep.reservation.reservationdescription.domain.ReservationDescription;
import java.util.List;

public interface GetReservationsDescriptionByIdReservation {
    List<ReservationDescription> getReservationsDescriptionByIdReservation(String idReservation);
}
