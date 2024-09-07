package com.eatsleep.user.employee.infrastructure.outputadapters.db;

import com.eatsleep.user.common.utils.PasswordUtils;
import com.eatsleep.user.employee.domain.Employee;
import com.eatsleep.user.security.Role;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.time.LocalDateTime;
import java.util.UUID;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Entity
@Table(name = "employee")
@Getter
@Setter
@NoArgsConstructor
public class EmployeeDbEntity {
    @Id
    @Column(name = "id", columnDefinition = "CHAR(36)")
    private String id;

    @Column(name = "email", nullable = false, length = 255)
    private String email;

    @Column(name = "password", nullable = false, length = 255)
    private String password;

    @Column(name = "name", nullable = false, length = 255)
    private String name;

    @Column(name = "phone", length = 20)
    private String phone;

    @Column(name = "type", length = 50)
    private String type;

    @Column(name = "weekly_payment")
    private double weeklyPayment;

    @Column(name = "id_location", columnDefinition = "CHAR(36)")
    private String idLocation;
    
    @Enumerated(EnumType.STRING)
    @Column(name = "role")
    private Role role;

    @Column(name = "expiration_date")
    private LocalDateTime expirationDate;
    
    public Employee toDomainModel() {

        return Employee.builder()
                .id(UUID.fromString(this.getId()))
                .email(this.getEmail())
                .password(this.getPassword())
                .name(this.getName())
                .phone(this.getPhone())
                .type(this.getType())
                .weeklyPayment(this.getWeeklyPayment())
                .idLocation(UUID.fromString(this.getIdLocation()))
                .expirationDate(this.getExpirationDate())
                .role(this.role)
                .build();
    }
    
    
    public static EmployeeDbEntity from(Employee employee) {
        if (employee == null) {
            return null;
        }

        EmployeeDbEntity employeeDbEntity = new EmployeeDbEntity();
        employeeDbEntity.setId(employee.getId().toString());
        employeeDbEntity.setEmail(employee.getEmail());
        employeeDbEntity.setPassword(employee.getPassword());
        employeeDbEntity.setName(employee.getName());
        employeeDbEntity.setPhone(employee.getPhone());
        employeeDbEntity.setType(employee.getType());
        employeeDbEntity.setWeeklyPayment(employee.getWeeklyPayment());
        employeeDbEntity.setIdLocation(employee.getIdLocation().toString());
        employeeDbEntity.setExpirationDate(employee.getExpirationDate() != null ? employee.getExpirationDate() : null);
        employeeDbEntity.setRole(employee.getRole() != null ? employee.getRole() : Role.EMPLOYEE);

        return employeeDbEntity;
    }
    
}
