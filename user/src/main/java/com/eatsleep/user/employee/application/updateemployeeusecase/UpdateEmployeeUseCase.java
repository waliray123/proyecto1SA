package com.eatsleep.user.employee.application.updateemployeeusecase;

import com.eatsleep.user.common.UseCase;
import com.eatsleep.user.employee.domain.Employee;
import com.eatsleep.user.employee.infrastructure.inputports.UpdateEmployeeInputPort;
import com.eatsleep.user.employee.infrastructure.outputadapters.db.EmployeeDbOutputAdapter;
import jakarta.transaction.Transactional;
import java.util.Optional;
import java.util.UUID;
import org.springframework.beans.factory.annotation.Autowired;

@UseCase
@Transactional
public class UpdateEmployeeUseCase implements UpdateEmployeeInputPort{
    
    private EmployeeDbOutputAdapter employeeOutputAdapter;

    @Autowired
    public UpdateEmployeeUseCase(EmployeeDbOutputAdapter employeeOutputAdapter) {
        this.employeeOutputAdapter = employeeOutputAdapter;
    }

    @Override
    public Optional<Employee> updateEmployee(String idEmployee, UpdateEmployeeRequest employeeRequest, String typeLocation, String idLocation) {
        // Validar la información del employee
        validateEmployeeRequest(employeeRequest,idLocation);
        
        // Validar la location
        if(typeLocation.equalsIgnoreCase("hotel")){
            // Validar que existe la ubicación (hotel)
            validateHotel(idLocation);
        } else if(typeLocation.equalsIgnoreCase("restaurant")){
            // Validar que existe la ubicación (restaurante)
            validateRestaurant(idLocation);
        }

        // Buscar el employee existente en la base de datos
        Optional<Employee> existingEmployeeOptional = employeeOutputAdapter.getEmployeeById(idEmployee);
        if (existingEmployeeOptional.isEmpty()) {
            return Optional.empty();  // Employee no encontrado, retorno vacío
        }

        // Actualizar los campos del employee existente
        Employee existingEmployee = existingEmployeeOptional.get();
        existingEmployee.setEmail(employeeRequest.getEmail());
        existingEmployee.setPassword(employeeRequest.getPassword());
        existingEmployee.setName(employeeRequest.getName());
        existingEmployee.setPhone(employeeRequest.getPhone());
        existingEmployee.setType(employeeRequest.getType());
        existingEmployee.setWeeklyPayment(employeeRequest.getWeeklyPayment());
        existingEmployee.setIdLocation(UUID.fromString(idLocation));

        // Guardar el employee actualizado
        Optional<Employee> updatedEmployeeOptional = employeeOutputAdapter.updateEmployee(idEmployee, existingEmployee);
        if(updatedEmployeeOptional.isPresent()){
            updatedEmployeeOptional.get().setPassword(employeeRequest.getPassword());
        }
        
        // Crear y devolver la respuesta
        return updatedEmployeeOptional;
    }
    
    private void validateEmployeeRequest(UpdateEmployeeRequest request, String idLocation) {
        if (request.getPassword() != null && request.getPassword().length() < 6) {
            throw new IllegalArgumentException("La contrasena tiene que se mayor a 6 digitos");
        }
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
        // Simple email validation (can be improved)
        String emailRegex = "^[a-zA-Z0-9_+&*-]+(?:\\.[a-zA-Z0-9_+&*-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,7}$";
        return email.matches(emailRegex);
    }
    
    private void validateHotel(String idLocation) {
        //TODO : validate hotel
        // Check if the location (hotel) exists. This could involve querying the database or another service.
        //Optional<EmployeeDbEntity> locationOpt = employeeRepository.findById(UUID.fromString(idLocation));
        /*if (locationOpt.isEmpty()) {
            throw new IllegalArgumentException("Location does not exist");
        }*/
    }
    
    private void validateRestaurant(String idLocation) {
        // Check if the location (hotel) exists. This could involve querying the database or another service.
        //Optional<EmployeeDbEntity> locationOpt = employeeRepository.findById(UUID.fromString(idLocation));
        /*if (locationOpt.isEmpty()) {
            throw new IllegalArgumentException("Location does not exist");
        }*/
    }



}
