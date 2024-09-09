package com.eatsleep.user.employee.application.getallpaymentsusecase;

import com.eatsleep.user.common.UseCase;
import com.eatsleep.user.employee.infrastructure.inputports.GetAllPaymentsInputPort;
import com.eatsleep.user.payment.domain.Payment;
import com.eatsleep.user.payment.infrastructure.outputadapters.db.PaymentDbOutputAdapter;
import jakarta.transaction.Transactional;
import java.time.LocalDate;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;

@UseCase
@Transactional
public class GetAllPaymentsUseCase implements GetAllPaymentsInputPort{
    
    private PaymentDbOutputAdapter paymentDbOutputAdapter;

    @Autowired
    public GetAllPaymentsUseCase(PaymentDbOutputAdapter paymentDbOutputAdapter) {
        this.paymentDbOutputAdapter = paymentDbOutputAdapter;
    }

    @Override
    public List<Payment> getAllPayments(LocalDate startDate, LocalDate endDate) {
        return this.paymentDbOutputAdapter.getAllPayments(startDate, endDate);
    }


}
