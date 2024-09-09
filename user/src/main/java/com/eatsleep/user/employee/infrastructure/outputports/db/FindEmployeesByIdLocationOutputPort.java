package com.eatsleep.user.employee.infrastructure.outputports.db;

import com.eatsleep.user.employee.domain.Employee;
import java.util.List;

public interface FindEmployeesByIdLocationOutputPort {
    
    List<Employee> findEmployeesByIdLocationInputPort(String idLocation);

}
