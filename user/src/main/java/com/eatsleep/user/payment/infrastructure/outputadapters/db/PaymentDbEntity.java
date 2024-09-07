package com.eatsleep.user.payment.infrastructure.outputadapters.db;

import com.eatsleep.user.employee.infrastructure.outputadapters.db.EmployeeDbEntity;
import com.eatsleep.user.payment.domain.Payment;
import jakarta.persistence.*;
import java.time.LocalDate;
import java.util.UUID;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "payment")
public class PaymentDbEntity {

    @Id
    @Column(name = "id", nullable = false, columnDefinition = "CHAR(36)")
    private String id;

    @Column(name = "date_payment", nullable = false)
    private LocalDate datePayment;

    @Column(name = "payment", nullable = false)
    private Double paymentAmount;

    @ManyToOne
    @JoinColumn(name = "id_employee", nullable = false)
    private EmployeeDbEntity employee;

    public Payment toDomainModel() {
        return Payment.builder()
                .id(UUID.fromString(this.id))
                .datePayment(this.datePayment)
                .paymentAmount(this.paymentAmount)
                .employee(this.employee != null ? this.employee.toDomainModel() : null)
                .build();
    }

    public static PaymentDbEntity from(Payment payment) {
        PaymentDbEntity entity = new PaymentDbEntity();
        entity.setId(payment.getId() != null ? payment.getId().toString() : UUID.randomUUID().toString());
        entity.setDatePayment(payment.getDatePayment());
        entity.setPaymentAmount(payment.getPaymentAmount());
        entity.setEmployee(EmployeeDbEntity.from(payment.getEmployee()));
        return entity;
    }
}
