package com.eatsleep.user.employee.infrastructure.inputports;

import com.eatsleep.user.employee.domain.Employee;
import java.util.List;
import java.util.Optional;

public interface RetrieveEmployeeInputPort {
    Optional<Employee> getEmployeeById(String id);
    Optional<Employee> getEmployeeByEmail(String email);
    List<Employee> getAllEmployees();
}
