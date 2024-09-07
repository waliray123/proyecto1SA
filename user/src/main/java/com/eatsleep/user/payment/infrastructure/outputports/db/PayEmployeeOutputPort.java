package com.eatsleep.user.payment.infrastructure.outputports.db;

import com.eatsleep.user.payment.domain.Payment;

public interface PayEmployeeOutputPort {
    Payment payEmployee(Payment payment);
}
