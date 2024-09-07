package com.eatsleep.bill.bill.infrastructure.inputadapters.restapi.response;

import com.eatsleep.bill.bill.domain.Bill;
import java.time.LocalDate;
import lombok.Value;


@Value
public class PayBillRestaurantResponse {
    private String id;
    private String type;
    private String idLocation;
    private LocalDate startDate;
    private double total;
    private String userId;

    public PayBillRestaurantResponse(Bill bill) {
        this.id = bill.getId().toString();
        this.type = bill.getType();
        this.idLocation = bill.getIdLocation().toString();
        this.startDate = bill.getStartDate();
        this.total = bill.getTotal();
        this.userId = bill.getUserId().toString();
    }

}
