package com.eatsleep.bill.bill.application.payrestaurantbillusecase;

import com.eatsleep.bill.billdescription.application.paybillusecase.PayBillDescriptionRequest;
import java.time.LocalDate;
import java.util.List;
import lombok.Value;

@Value
public class PayBillRestaurantRequest {
    private LocalDate startDate;
    List<PayBillDescriptionRequest> description_products;
}
