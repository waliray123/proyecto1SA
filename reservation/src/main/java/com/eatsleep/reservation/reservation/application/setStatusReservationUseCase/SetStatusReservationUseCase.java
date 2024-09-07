package com.eatsleep.reservation.reservation.application.setStatusReservationUseCase;

import com.eatsleep.reservation.common.UseCase;
import com.eatsleep.reservation.reservation.domain.Reservation;
import com.eatsleep.reservation.reservation.domain.Status;
import com.eatsleep.reservation.reservation.infrastructure.inputports.SetStatusReservationInputPort;
import com.eatsleep.reservation.reservation.infrastructure.outputadapters.db.ReservationDbOutputAdapter;
import jakarta.transaction.Transactional;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;


@UseCase
@Transactional
public class SetStatusReservationUseCase implements SetStatusReservationInputPort{
    
    private ReservationDbOutputAdapter reservationDbOutputAdapter;

    @Autowired
    public SetStatusReservationUseCase(ReservationDbOutputAdapter reservationDbOutputAdapter) {
        this.reservationDbOutputAdapter = reservationDbOutputAdapter;
    }

    @Override
    public boolean setStatusReservation(String idReservation, String status) {
        Optional<Reservation> reservationOpt = this.reservationDbOutputAdapter.findReservationByIdReservation(idReservation);
        if(reservationOpt.isEmpty()){
            return false;
        }
        Reservation reservation = reservationOpt.get();
        
        if(status.contains("confirmed")){
            reservation.setStatus(Status.CONFIRMED);
        }else if(status.contains("cancelled")){
            reservation.setStatus(Status.CANCELLED);
        }else{
            return false;
        }
        
        reservation = this.reservationDbOutputAdapter.updateReservation(reservation);
        if(reservation == null){
            return false;
        }
        return true;
        
    }

}
