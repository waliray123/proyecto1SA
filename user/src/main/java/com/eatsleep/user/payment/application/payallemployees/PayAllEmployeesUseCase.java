package com.eatsleep.user.payment.application.payallemployees;

import com.eatsleep.user.common.UseCase;
import com.eatsleep.user.employee.domain.Employee;
import com.eatsleep.user.employee.infrastructure.outputadapters.db.EmployeeDbOutputAdapter;
import com.eatsleep.user.payment.domain.Payment;
import com.eatsleep.user.payment.infrastructure.inputports.PayAllEmployeesInputPort;
import com.eatsleep.user.payment.infrastructure.outputadapters.db.PaymentDbOutputAdapter;
import jakarta.transaction.Transactional;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;

@UseCase
@Transactional
public class PayAllEmployeesUseCase implements PayAllEmployeesInputPort{
    
    private EmployeeDbOutputAdapter employeeOutputAdapter;
    private PaymentDbOutputAdapter paymentOutputAdapter;

    @Autowired
    public PayAllEmployeesUseCase(EmployeeDbOutputAdapter employeeOutputAdapter, PaymentDbOutputAdapter paymentOutputAdapter) {
        this.employeeOutputAdapter = employeeOutputAdapter;
        this.paymentOutputAdapter = paymentOutputAdapter;
    }

    @Override
    public List<Payment> payAllEmployees() {
        List<Employee> employees = this.employeeOutputAdapter.getAllEmployees();
        List<Payment> payments = new ArrayList<>();
        
        
        for (Employee employee : employees) {
            Payment payment = Payment.builder()
                .datePayment(LocalDate.now())
                .paymentAmount(employee.getWeeklyPayment())
                .employee(employee)
                .build();
            
            payment = this.paymentOutputAdapter.payEmployee(payment);
            payments.add(payment);
        }
        
        return payments;
    }

}
