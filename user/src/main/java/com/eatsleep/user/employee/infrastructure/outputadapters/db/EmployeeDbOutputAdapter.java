package com.eatsleep.user.employee.infrastructure.outputadapters.db;

import com.eatsleep.user.common.OutputAdapter;
import com.eatsleep.user.employee.domain.Employee;
import com.eatsleep.user.employee.infrastructure.outputports.db.CreateEmployeeOutputPort;
import com.eatsleep.user.employee.infrastructure.outputports.db.FindEmployeesByIdLocationOutputPort;
import com.eatsleep.user.employee.infrastructure.outputports.db.RetrieveEmployeeOutputPort;
import com.eatsleep.user.employee.infrastructure.outputports.db.UpdateEmployeeOutputPort;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@OutputAdapter
public class EmployeeDbOutputAdapter implements CreateEmployeeOutputPort
        ,UpdateEmployeeOutputPort
        ,RetrieveEmployeeOutputPort
        ,FindEmployeesByIdLocationOutputPort{
    
    private EmployeeDbEntityRepository employeeDbEntityRepository;

    public EmployeeDbOutputAdapter(EmployeeDbEntityRepository employeeDbEntityRepository) {
        this.employeeDbEntityRepository = employeeDbEntityRepository;
    }

    @Override
    public Employee createEmployee(Employee employee) {
        EmployeeDbEntity employeeDbEntity = EmployeeDbEntity.from(employee);
        employeeDbEntity = employeeDbEntityRepository.save(employeeDbEntity);
        return employeeDbEntity.toDomainModel();
    }
    
    @Override
    public Optional<Employee> updateEmployee(String id, Employee updatedEmployee) {
        Optional<EmployeeDbEntity> existingEmployeeOpt = employeeDbEntityRepository.findById(id);

        if (existingEmployeeOpt.isPresent()) {
            EmployeeDbEntity updatedEntityEmployee = EmployeeDbEntity.from(updatedEmployee);
            
            EmployeeDbEntity savedEmployee = employeeDbEntityRepository.save(updatedEntityEmployee);

            return Optional.of(savedEmployee.toDomainModel());
        } else {
            return Optional.empty();
        }
    }

    @Override
    public Optional<Employee> getEmployeeById(String id) {
        Optional<EmployeeDbEntity> employeeEntity = employeeDbEntityRepository.findById(id);
        return employeeEntity
                .map(employeeDbEntity -> employeeDbEntity.toDomainModel());
    }

    @Override
    public List<Employee> getAllEmployees() {
        return employeeDbEntityRepository.findAll().stream()
                .map(employeeDbEntity -> employeeDbEntity.toDomainModel())
                .collect(Collectors.toList());
    }

    @Override
    public Optional<Employee> getEmployeeByEmail(String email) {
        Optional<EmployeeDbEntity> employeeEntity = employeeDbEntityRepository.findByEmail(email);
        return employeeEntity
                .map(employeeDbEntity -> employeeDbEntity.toDomainModel());
    }

    @Override
    public List<Employee> findEmployeesByIdLocationInputPort(String idLocation) {
        // Obtiene la lista de EmployeeDbEntity a través del repositorio
        List<EmployeeDbEntity> employeeDbEntities = employeeDbEntityRepository.findByIdLocation(idLocation);
        
        // Convierte la lista de EmployeeDbEntity a Employee usando toDomainModel()
        return employeeDbEntities.stream()
                .map(EmployeeDbEntity::toDomainModel)
                .collect(Collectors.toList());
    }

}
