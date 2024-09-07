package com.eatsleep.user.employee.application.retrieveemployeeusecase;

import com.eatsleep.user.common.UseCase;
import com.eatsleep.user.employee.domain.Employee;
import com.eatsleep.user.employee.infrastructure.inputports.RetrieveEmployeeInputPort;
import com.eatsleep.user.employee.infrastructure.outputadapters.db.EmployeeDbOutputAdapter;
import jakarta.transaction.Transactional;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;

@UseCase
@Transactional
public class RetrieveEmployeeUseCase implements RetrieveEmployeeInputPort{
    
    private EmployeeDbOutputAdapter employeeOutputAdapter;

    @Autowired
    public RetrieveEmployeeUseCase(EmployeeDbOutputAdapter employeeOutputAdapter) {
        this.employeeOutputAdapter = employeeOutputAdapter;
    }

    @Override
    public Optional<Employee> getEmployeeById(String id) {
        // Recuperar el employee por ID
        Optional<Employee> employeeEntityOptional = employeeOutputAdapter.getEmployeeById(id);

        return employeeEntityOptional;
    }

    @Override
    public List<Employee> getAllEmployees() {
        // Recuperar todos los employeees
        return employeeOutputAdapter.getAllEmployees();
    }

    @Override
    public Optional<Employee> getEmployeeByEmail(String email) {
        return employeeOutputAdapter.getEmployeeByEmail(email);
    }

}
