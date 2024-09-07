package com.eatsleep.user.payment.domain;

import com.eatsleep.user.common.DomainEntity;
import com.eatsleep.user.employee.domain.Employee;
import java.time.LocalDate;
import java.util.UUID;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
@DomainEntity
public class Payment {
    private UUID id;
    private LocalDate datePayment;
    private Double paymentAmount;
    private Employee employee;
}
