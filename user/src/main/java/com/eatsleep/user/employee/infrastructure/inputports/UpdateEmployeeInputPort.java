package com.eatsleep.user.employee.infrastructure.inputports;

import com.eatsleep.user.employee.application.updateemployeeusecase.UpdateEmployeeRequest;
import com.eatsleep.user.employee.domain.Employee;
import java.util.Optional;

public interface UpdateEmployeeInputPort {
    Optional<Employee> updateEmployee(String idEmployee, UpdateEmployeeRequest employee, String typeLocation, String idLocation);
}
