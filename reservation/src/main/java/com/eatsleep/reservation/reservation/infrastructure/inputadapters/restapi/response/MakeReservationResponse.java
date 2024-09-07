package com.eatsleep.reservation.reservation.infrastructure.inputadapters.restapi.response;

import com.eatsleep.reservation.reservation.domain.Reservation;
import com.eatsleep.reservation.reservation.domain.Status;
import java.time.LocalDate;
import lombok.Value;

@Value
public class MakeReservationResponse {
    private String id;
    private String idLocation;
    private LocalDate dateStart;
    private LocalDate dateEnd;
    private double total;
    private Status status;
    private String user;
    
    public MakeReservationResponse(Reservation reservation) {
        this.id = reservation.getId().toString();
        this.idLocation = reservation.getIdLocation().toString();
        this.dateStart = reservation.getDateStart();
        this.dateEnd = reservation.getDateEnd();
        this.total = reservation.getTotal();
        this.status = reservation.getStatus();
        this.user = reservation.getUser().toString();
    }
}
