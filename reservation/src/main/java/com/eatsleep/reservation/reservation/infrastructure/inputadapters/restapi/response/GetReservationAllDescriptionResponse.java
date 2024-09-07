package com.eatsleep.reservation.reservation.infrastructure.inputadapters.restapi.response;

import com.eatsleep.reservation.reservation.domain.Reservation;
import com.eatsleep.reservation.reservation.domain.Status;
import com.eatsleep.reservation.reservationdescription.domain.ReservationDescription;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import lombok.Value;

@Value
public class GetReservationAllDescriptionResponse {
    private String id;
    private String idLocation;
    private LocalDate dateStart;
    private LocalDate dateEnd;
    private double total;
    private Status status;
    private String user;
    private List<GetReservationDescriptionResponse> descriptions;
    
    public GetReservationAllDescriptionResponse(Reservation reservation) {
        this.descriptions = new ArrayList<>();
        this.id = reservation.getId().toString();
        this.idLocation = reservation.getIdLocation().toString();
        this.dateStart = reservation.getDateStart();
        this.dateEnd = reservation.getDateEnd();
        this.total = reservation.getTotal();
        this.status = reservation.getStatus();
        this.user = reservation.getUser().toString();
        for (ReservationDescription description : reservation.getDescriptions()) {
            GetReservationDescriptionResponse descriptionResponse = new GetReservationDescriptionResponse(description);
            this.descriptions.add(descriptionResponse);
        }
    }
}
