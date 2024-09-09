package com.eatsleep.user.employee.infrastructure.inputports;

import com.eatsleep.user.employee.domain.Employee;
import java.util.List;

public interface FindEmployeesByIdLocationInputPort {
    
    List<Employee> findEmployeesByIdLocationInputPort(String idLocation);

}
