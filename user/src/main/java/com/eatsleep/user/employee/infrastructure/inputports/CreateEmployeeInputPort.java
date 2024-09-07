package com.eatsleep.user.employee.infrastructure.inputports;

import com.eatsleep.user.employee.application.createemployeeusecase.CreateEmployeeRequest;
import com.eatsleep.user.employee.application.exceptions.EmployeeAlreadyExistsException;
import com.eatsleep.user.employee.domain.Employee;

public interface CreateEmployeeInputPort {
    
    Employee createEmployee(CreateEmployeeRequest employee, String typeLocation, String idLocation) throws EmployeeAlreadyExistsException;

}
