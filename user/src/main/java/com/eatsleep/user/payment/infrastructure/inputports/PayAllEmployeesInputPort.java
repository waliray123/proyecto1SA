package com.eatsleep.user.payment.infrastructure.inputports;

import com.eatsleep.user.payment.domain.Payment;
import java.util.List;

public interface PayAllEmployeesInputPort {
    
    List<Payment> payAllEmployees();

}
