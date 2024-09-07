package com.eatsleep.reservation.reservation.application.getreservationsbyuserhotel;

import com.eatsleep.reservation.reservation.application.makereservation.*;
import com.eatsleep.reservation.common.UseCase;
import com.eatsleep.reservation.reservation.application.exceptions.ReservationException;
import com.eatsleep.reservation.reservation.domain.Reservation;
import com.eatsleep.reservation.reservation.infrastructure.inputports.GetReservationsByUserHotelInputPort;
import com.eatsleep.reservation.reservation.infrastructure.outputadapters.db.ReservationDbOutputAdapter;
import com.eatsleep.reservation.reservationdescription.domain.ReservationDescription;
import com.eatsleep.reservation.reservationdescription.infrastructure.outputadapters.db.ReservationDescriptionDbOutputAdapter;
import jakarta.transaction.Transactional;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;

@UseCase
@Transactional
public class GetReservationsByUserHotelUseCase implements GetReservationsByUserHotelInputPort{
    
    private ReservationDbOutputAdapter reservationDbOutputAdapter;
    private ReservationDescriptionDbOutputAdapter reservationDescriptionDbOutputAdapter;

    @Autowired
    public GetReservationsByUserHotelUseCase(ReservationDbOutputAdapter reservationDbOutputAdapter
            , ReservationDescriptionDbOutputAdapter reservationDescriptionDbOutputAdapter) {
        this.reservationDbOutputAdapter = reservationDbOutputAdapter;
        this.reservationDescriptionDbOutputAdapter = reservationDescriptionDbOutputAdapter;
    }

    @Override
    public List<Reservation> getReservationByUserHotel(String idUser, String idHotel) throws ReservationException {
        if (idUser == null) {
            throw new IllegalArgumentException("El usuario es obligatorio al realizar una reservacion");
        }
        if (idHotel == null) {
            throw new IllegalArgumentException("El hotel es un campo obligatorio al realizar una reservacion");
        }
        
        List<Reservation> reservations = this.reservationDbOutputAdapter.findReservationByUserHotel(idUser, idHotel);
        
        for (Reservation reservation : reservations) {
            List<ReservationDescription> descriptions = this.reservationDescriptionDbOutputAdapter.getReservationsDescriptionByIdReservation(reservation.getId().toString());
            reservation.setDescriptions(descriptions);
        }
        
        return reservations;
    }
    
    

}
