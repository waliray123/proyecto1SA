package com.eatsleep.user.employee.infrastructure.outputadapters.db;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EmployeeDbEntityRepository extends JpaRepository<EmployeeDbEntity, String>{
    Optional<EmployeeDbEntity> findByEmail(String email);
}
