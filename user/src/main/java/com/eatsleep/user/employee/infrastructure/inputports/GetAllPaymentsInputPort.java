package com.eatsleep.user.employee.infrastructure.inputports;

import com.eatsleep.user.payment.domain.Payment;
import java.time.LocalDate;
import java.util.List;

public interface GetAllPaymentsInputPort {
    List<Payment> getAllPayments(LocalDate startDate, LocalDate endDate);
}
