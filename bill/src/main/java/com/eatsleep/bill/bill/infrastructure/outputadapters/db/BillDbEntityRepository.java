package com.eatsleep.bill.bill.infrastructure.outputadapters.db;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface BillDbEntityRepository extends JpaRepository<BillDbEntity, String>{
    @Query("SELECT b.userId, SUM(b.total) AS totalSpent " +
               "FROM BillDbEntity b " +
               "GROUP BY b.userId " +
               "ORDER BY totalSpent DESC")
    List<Object[]> findTopSpendingClients();
}
