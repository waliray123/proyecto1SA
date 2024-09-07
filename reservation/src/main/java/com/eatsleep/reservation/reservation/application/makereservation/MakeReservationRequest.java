package com.eatsleep.reservation.reservation.application.makereservation;

import java.time.LocalDate;
import java.util.List;
import lombok.Value;

@Value
public class MakeReservationRequest {
    private LocalDate dateStart;
    private LocalDate dateEnd;
    private double total;
    private List<String> idRooms;
}
