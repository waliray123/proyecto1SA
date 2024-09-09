package com.eatsleep.user.payment.infrastructure.outputadapters.db;

import java.time.LocalDate;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface PaymentDbEntityRepository extends JpaRepository<PaymentDbEntity, String>{
    @Query(
        value = "SELECT * FROM payment " +
                "WHERE date_payment BETWEEN :startDate AND :endDate",
        nativeQuery = true
    )
    List<PaymentDbEntity> findPaymentsBetweenDates(
        @Param("startDate") LocalDate startDate,
        @Param("endDate") LocalDate endDate
    );
}
