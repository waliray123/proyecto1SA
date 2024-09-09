package com.eatsleep.reports.application.allearningscostsusecase;

import java.time.LocalDate;
import lombok.Value;

@Value
public class PaymentResponse {
    private String id;
    private LocalDate datePayment;
    private Double paymentAmount;
    private String employeeId;
}
