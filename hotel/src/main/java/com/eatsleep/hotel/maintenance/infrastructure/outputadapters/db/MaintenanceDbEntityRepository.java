package com.eatsleep.hotel.maintenance.infrastructure.outputadapters.db;

import java.time.LocalDate;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface MaintenanceDbEntityRepository extends JpaRepository<MaintenanceDbEntity, String>{
    @Query(
        value = "SELECT * FROM maintenance " +
                "WHERE date_maintenance BETWEEN :startDate AND :endDate",
        nativeQuery = true
    )
    List<MaintenanceDbEntity> findAllMaintenancesBetweenDates(
        @Param("startDate") LocalDate startDate,
        @Param("endDate") LocalDate endDate
    );
}
