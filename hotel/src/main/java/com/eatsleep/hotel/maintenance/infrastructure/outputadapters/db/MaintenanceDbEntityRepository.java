package com.eatsleep.hotel.maintenance.infrastructure.outputadapters.db;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MaintenanceDbEntityRepository extends JpaRepository<MaintenanceDbEntity, String>{
    
}
