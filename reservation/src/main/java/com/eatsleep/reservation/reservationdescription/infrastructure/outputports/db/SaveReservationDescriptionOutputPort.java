package com.eatsleep.reservation.reservationdescription.infrastructure.outputports.db;

import com.eatsleep.reservation.reservationdescription.domain.ReservationDescription;
import java.time.LocalDate;
import java.util.Optional;

public interface SaveReservationDescriptionOutputPort {
    ReservationDescription saveReservationDescription(ReservationDescription reservationDescription);
    
    Optional<ReservationDescription> findReservationByRoomAndDatePendingConfirmed(String idRoom, LocalDate date);
}
