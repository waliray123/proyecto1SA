package com.eatsleep.reservation.reservation.application.getreservationbyidreservation;

import com.eatsleep.reservation.common.UseCase;
import com.eatsleep.reservation.reservation.application.exceptions.ReservationException;
import com.eatsleep.reservation.reservation.domain.Reservation;
import com.eatsleep.reservation.reservation.infrastructure.inputports.GetReservationByIdReservationInputPort;
import com.eatsleep.reservation.reservation.infrastructure.outputadapters.db.ReservationDbOutputAdapter;
import com.eatsleep.reservation.reservationdescription.domain.ReservationDescription;
import com.eatsleep.reservation.reservationdescription.infrastructure.outputadapters.db.ReservationDescriptionDbOutputAdapter;
import jakarta.transaction.Transactional;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;


@UseCase
@Transactional
public class GetReservationByIdReservationUseCase implements GetReservationByIdReservationInputPort{
    
    private ReservationDbOutputAdapter reservationDbOutputAdapter;
    private ReservationDescriptionDbOutputAdapter reservationDescriptionDbOutputAdapter;

    @Autowired
    public GetReservationByIdReservationUseCase(ReservationDbOutputAdapter reservationDbOutputAdapter
            , ReservationDescriptionDbOutputAdapter reservationDescriptionDbOutputAdapter) {
        this.reservationDbOutputAdapter = reservationDbOutputAdapter;
        this.reservationDescriptionDbOutputAdapter = reservationDescriptionDbOutputAdapter;
    }

    @Override
    public Optional<Reservation> getReservationByIdReservation(String idReservation) throws ReservationException {
        Optional<Reservation> reservationOpt = this.reservationDbOutputAdapter.findReservationByIdReservation(idReservation);
        
        if(reservationOpt.isPresent()){
            List<ReservationDescription> descriptions = this.reservationDescriptionDbOutputAdapter.getReservationsDescriptionByIdReservation(idReservation);
            reservationOpt.get().setDescriptions(descriptions);
        }
        
        return reservationOpt;
    }

}
