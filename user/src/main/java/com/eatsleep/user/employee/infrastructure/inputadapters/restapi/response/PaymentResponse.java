package com.eatsleep.user.employee.infrastructure.inputadapters.restapi.response;

import com.eatsleep.user.payment.domain.Payment;
import java.time.LocalDate;
import lombok.Value;

@Value
public class PaymentResponse {
    private String id;
    private LocalDate datePayment;
    private Double paymentAmount;
    private String employeeId;

    public PaymentResponse(Payment payment) {
        this.id = payment.getId().toString();
        this.datePayment = payment.getDatePayment();
        this.paymentAmount = payment.getPaymentAmount();
        this.employeeId = payment.getEmployee().getId().toString();
    }
    
    
}

