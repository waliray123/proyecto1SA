package com.eatsleep.bill.bill.infrastructure.inputadapters.restapi.response;

import com.eatsleep.bill.bill.domain.Bill;
import java.time.LocalDate;
import lombok.Value;


@Value
public class PayBillHotelResponse {
    private String id;
    private String type;
    private String idLocation;
    private LocalDate startDate;
    private LocalDate endDate;
    private double total;
    private String userId;
    private String reservationId;

    public PayBillHotelResponse(Bill bill) {
        this.id = bill.getId().toString();
        this.type = bill.getType();
        this.idLocation = bill.getIdLocation().toString();
        this.startDate = bill.getStartDate();
        this.endDate = bill.getEndDate();
        this.total = bill.getTotal();
        this.userId = bill.getUserId().toString();
        this.reservationId = bill.getReservationId() != null ? bill.getReservationId().toString() : null;
    }

}
