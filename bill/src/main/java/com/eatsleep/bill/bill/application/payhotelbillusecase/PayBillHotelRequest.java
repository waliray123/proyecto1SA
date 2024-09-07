package com.eatsleep.bill.bill.application.payhotelbillusecase;

import com.eatsleep.bill.billdescription.application.paybillusecase.PayBillDescriptionRequest;
import java.time.LocalDate;
import java.util.List;
import lombok.Value;

@Value
public class PayBillHotelRequest {
    private LocalDate startDate;
    private LocalDate endDate;
    private String reservationId;
    List<PayBillDescriptionRequest> description_products;
}
