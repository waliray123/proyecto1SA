package com.eatsleep.user.payment.infrastructure.outputadapters.db;

import com.eatsleep.user.common.OutputAdapter;
import com.eatsleep.user.payment.domain.Payment;
import com.eatsleep.user.payment.infrastructure.outputports.db.PayEmployeeOutputPort;
import java.util.List;

@OutputAdapter
public class PaymentDbOutputAdapter implements PayEmployeeOutputPort{
    
    private PaymentDbEntityRepository paymentDbEntityRepository;

    public PaymentDbOutputAdapter(PaymentDbEntityRepository paymentDbEntityRepository) {
        this.paymentDbEntityRepository = paymentDbEntityRepository;
    }

    @Override
    public Payment payEmployee(Payment payment) {
        PaymentDbEntity paymentDbEntity = PaymentDbEntity.from(payment);
        return paymentDbEntityRepository.save(paymentDbEntity).toDomainModel();
    }

    

}
