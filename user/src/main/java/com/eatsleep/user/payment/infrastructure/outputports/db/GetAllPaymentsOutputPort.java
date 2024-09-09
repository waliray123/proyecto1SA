package com.eatsleep.user.payment.infrastructure.outputports.db;

import com.eatsleep.user.payment.domain.Payment;
import java.time.LocalDate;
import java.util.List;

public interface GetAllPaymentsOutputPort {
    List<Payment> getAllPayments(LocalDate startDate, LocalDate endDate);
}
