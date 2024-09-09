package com.eatsleep.user.employee.application.findmployeesbylocationusecase;

import com.eatsleep.user.common.UseCase;
import com.eatsleep.user.employee.domain.Employee;
import com.eatsleep.user.employee.infrastructure.inputports.FindEmployeesByIdLocationInputPort;
import com.eatsleep.user.employee.infrastructure.outputadapters.db.EmployeeDbOutputAdapter;
import jakarta.transaction.Transactional;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;

@UseCase
@Transactional
public class FindEmployeesByIdLocationUseCase implements FindEmployeesByIdLocationInputPort{
    
    private EmployeeDbOutputAdapter employeeOutputAdapter;

    @Autowired
    public FindEmployeesByIdLocationUseCase(EmployeeDbOutputAdapter employeeOutputAdapter) {
        this.employeeOutputAdapter = employeeOutputAdapter;
    }

    @Override
    public List<Employee> findEmployeesByIdLocationInputPort(String idLocation) {
        return this.employeeOutputAdapter.findEmployeesByIdLocationInputPort(idLocation);
    }


}
