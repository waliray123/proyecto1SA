package com.eatsleep.reports.infrastructure.outputports.restapi;

import com.eatsleep.reports.application.allearningscostsusecase.PaymentResponse;
import java.time.LocalDate;
import java.util.List;

public interface FindPaymentsBetweenDatesOutputPort {
    List<PaymentResponse> findPaymentsBetweenDates(LocalDate startDate, LocalDate endDate);
}
