package com.eatsleep.user.payment.infrastructure.outputadapters.db;

import com.eatsleep.user.common.OutputAdapter;
import com.eatsleep.user.payment.domain.Payment;
import com.eatsleep.user.payment.infrastructure.outputports.db.GetAllPaymentsOutputPort;
import com.eatsleep.user.payment.infrastructure.outputports.db.PayEmployeeOutputPort;
import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@OutputAdapter
public class PaymentDbOutputAdapter implements PayEmployeeOutputPort,GetAllPaymentsOutputPort{
    
    private PaymentDbEntityRepository paymentDbEntityRepository;

    public PaymentDbOutputAdapter(PaymentDbEntityRepository paymentDbEntityRepository) {
        this.paymentDbEntityRepository = paymentDbEntityRepository;
    }

    @Override
    public Payment payEmployee(Payment payment) {
        PaymentDbEntity paymentDbEntity = PaymentDbEntity.from(payment);
        return paymentDbEntityRepository.save(paymentDbEntity).toDomainModel();
    }

    @Override
    public List<Payment> getAllPayments(LocalDate startDate, LocalDate endDate) {
        List<PaymentDbEntity> paymentDbEntities = this.paymentDbEntityRepository.findPaymentsBetweenDates(startDate, endDate);
        return paymentDbEntities.stream()
                                .map(PaymentDbEntity::toDomainModel)
                                .collect(Collectors.toList());
    }

    

}
