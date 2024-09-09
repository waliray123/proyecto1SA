package com.eatsleep.bill.bill.infrastructure.inputadapters.restapi.response;

import com.eatsleep.bill.bill.domain.Bill;
import com.eatsleep.bill.billdescription.domain.BillDescription;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import lombok.Value;

@Value
public class BillResponse {
    private String id;
    private String type;
    private String idLocation;
    private LocalDate startDate;
    private LocalDate endDate;
    private double total;
    private String userId;
    private String reservationId;
    private List<TopsBillDescriptionResponse> descriptions;

    public BillResponse(Bill bill) {
        this.descriptions = new ArrayList<>();
        this.id = bill.getId().toString();
        this.type = bill.getType();
        this.idLocation = bill.getIdLocation().toString();
        this.startDate = bill.getStartDate();
        this.endDate = bill.getEndDate();
        this.total = bill.getTotal();
        this.userId = bill.getUserId().toString();
        this.reservationId = bill.getReservationId() != null ? bill.getReservationId().toString() : null;
        for (BillDescription description : bill.getDescriptions()) {
            TopsBillDescriptionResponse descriptionResponse = new TopsBillDescriptionResponse(description);
            this.descriptions.add(descriptionResponse);
        }
    }
}
