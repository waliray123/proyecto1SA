package com.eatsleep.user.employee.infrastructure.outputports.db;

import com.eatsleep.user.employee.domain.Employee;

public interface CreateEmployeeOutputPort {
    Employee createEmployee(Employee employee);
}
