package com.eatsleep.user.employee.infrastructure.outputports.db;

import com.eatsleep.user.employee.domain.Employee;
import java.util.Optional;

public interface UpdateEmployeeOutputPort {
    Optional<Employee> updateEmployee(String id, Employee updatedEmployee);
}
