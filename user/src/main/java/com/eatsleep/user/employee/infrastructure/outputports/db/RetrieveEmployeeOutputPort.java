package com.eatsleep.user.employee.infrastructure.outputports.db;

import com.eatsleep.user.employee.domain.Employee;
import java.util.List;
import java.util.Optional;

public interface RetrieveEmployeeOutputPort {
    Optional<Employee> getEmployeeById(String id);
    Optional<Employee> getEmployeeByEmail(String email);
    List<Employee> getAllEmployees();    
}
