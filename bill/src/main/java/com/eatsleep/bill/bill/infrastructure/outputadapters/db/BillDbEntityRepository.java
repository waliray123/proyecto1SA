package com.eatsleep.bill.bill.infrastructure.outputadapters.db;

import java.time.LocalDate;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface BillDbEntityRepository extends JpaRepository<BillDbEntity, String>{
    @Query("SELECT b.userId, SUM(b.total) AS totalSpent " +
               "FROM BillDbEntity b " +
               "GROUP BY b.userId " +
               "ORDER BY totalSpent DESC")
    List<Object[]> findTopSpendingClients();
    
    @Query(
        value = "SELECT id_location " +
                "FROM bill " +
                "WHERE type = 'RESTAURANT' " +
                "GROUP BY id_location " +
                "ORDER BY SUM(total) DESC " +
                "LIMIT 1",
        nativeQuery = true
    )
    String findTopSpendingLocationForRestaurant();
    
    @Query(
        value = "SELECT * FROM bill " +
                "WHERE start_date BETWEEN :startDate AND :endDate",
        nativeQuery = true
    )
    List<BillDbEntity> findBillsBetweenDates(
        @Param("startDate") LocalDate startDate,
        @Param("endDate") LocalDate endDate
    );
}
