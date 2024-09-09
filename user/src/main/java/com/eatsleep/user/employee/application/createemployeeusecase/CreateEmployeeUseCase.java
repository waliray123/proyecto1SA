package com.eatsleep.user.employee.application.createemployeeusecase;

import com.eatsleep.user.common.UseCase;
import com.eatsleep.user.common.utils.PasswordGenerator;
import com.eatsleep.user.employee.application.exceptions.EmployeeAlreadyExistsException;
import com.eatsleep.user.employee.domain.Employee;
import com.eatsleep.user.employee.infrastructure.inputports.CreateEmployeeInputPort;
import com.eatsleep.user.employee.infrastructure.outputadapters.db.EmployeeDbOutputAdapter;
import com.eatsleep.user.employee.infrastructure.outputadapters.restapi.EmployeeRestApiOutportAdapter;
import com.eatsleep.user.security.Role;
import jakarta.transaction.Transactional;
import java.util.UUID;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;

@UseCase
@Transactional
public class CreateEmployeeUseCase implements CreateEmployeeInputPort{
    
    private EmployeeDbOutputAdapter employeeOutputAdapter;
    private EmployeeRestApiOutportAdapter employeeRestApiOutportAdapter;
    private PasswordEncoder passwordEncoder;

    @Autowired
    public CreateEmployeeUseCase(EmployeeDbOutputAdapter employeeOutputAdapter
            ,EmployeeRestApiOutportAdapter employeeRestApiOutportAdapter
            ,PasswordEncoder passwordEncoder) {
        this.employeeOutputAdapter = employeeOutputAdapter;
        this.passwordEncoder = passwordEncoder;
        this.employeeRestApiOutportAdapter = employeeRestApiOutportAdapter;
    }

    @Override
    public Employee createEmployee(CreateEmployeeRequest employeeRequest, String typeLocation, String idLocation) throws EmployeeAlreadyExistsException{
        // Validar la información del empleado
        validateEmployeeRequest(employeeRequest,idLocation);
        
        // Validar que no exista un empleado con el mismo correo
        if (this.employeeOutputAdapter.getEmployeeByEmail(employeeRequest.getEmail()).isPresent()) {
            throw new EmployeeAlreadyExistsException("Un empleado con email: " + employeeRequest.getEmail() + " ya existe");
        }
        
        
        if(typeLocation.equalsIgnoreCase("hotel")){
            // Validar que existe la ubicación (hotel)
            if(!validateHotel(idLocation)){
                throw new EmployeeAlreadyExistsException("El hotel con id: " + idLocation+" no existe" );
            }
        } else if(typeLocation.equalsIgnoreCase("restaurant")){
            if(!validateRestaurant(idLocation)){
                throw new EmployeeAlreadyExistsException("El restaurante con id: " + idLocation+" no existe" );
            }
        }

        //Generar una password
        PasswordGenerator passwordGenerator = new PasswordGenerator();
        String password = passwordGenerator.generatePassword(8);
        Role rol = employeeRequest.getType().equals("Administrator") ? Role.ADMINISTRATOR : Role.EMPLOYEE;

        // Crear el empleado
        Employee employee = Employee.builder()
                .id(UUID.randomUUID())
                .email(employeeRequest.getEmail())
                .password(passwordEncoder.encode(password))
                .name(employeeRequest.getName())
                .phone(employeeRequest.getPhone())
                .type(employeeRequest.getType())
                .weeklyPayment(employeeRequest.getWeeklyPayment())
                .idLocation(UUID.fromString(idLocation))
                .role(rol)
                .build();

        // Persistir el empleado en la base de datos usando el Output Adapter
        Employee savedEmployee = employeeOutputAdapter.createEmployee(employee);
        savedEmployee.setPassword(password);

        return savedEmployee;
    }
    
    private void validateEmployeeRequest(CreateEmployeeRequest request, String idLocation) {
        if (request.getEmail() == null || request.getEmail().isEmpty()) {
            throw new IllegalArgumentException("Correo requerido");
        }
        if (!isValidEmail(request.getEmail())) {
            throw new IllegalArgumentException("Formato de email incorrecto");
        }
        if (request.getName() == null || request.getName().isEmpty()) {
            throw new IllegalArgumentException("El nombre es requerido");
        }
        if (request.getPhone() != null && !request.getPhone().matches("\\+?[0-9]*")) {
            throw new IllegalArgumentException("El numero de telefono solo puede tener digitos y tiene que empezar con '+'");
        }
        if (request.getType() == null || request.getType().isEmpty()) {
            throw new IllegalArgumentException("El tipo de cliente es requerido");
        }
        if (request.getWeeklyPayment() < 0) {
            throw new IllegalArgumentException("El pago semanal tiene que ser positivo");
        }
        if(idLocation == null || idLocation.isEmpty()){
            throw new IllegalArgumentException("La locacion no tiene que ser nula");
        }
    }
    
    private boolean isValidEmail(String email) {
        // Simple email validation
        String emailRegex = "^[a-zA-Z0-9_+&*-]+(?:\\.[a-zA-Z0-9_+&*-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,7}$";
        return email.matches(emailRegex);
    }
    
    private boolean validateHotel(String idLocation) {
        return this.employeeRestApiOutportAdapter.validateExistHotel(idLocation);
    }
    
    private boolean validateRestaurant(String idLocation) {
        return this.employeeRestApiOutportAdapter.validateExistRestaurant(idLocation);
    }

}
