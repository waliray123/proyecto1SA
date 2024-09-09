package com.eatsleep.promotion.promotion.infrastructure.outputadapters.db;

import java.time.LocalDate;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface PromotionDbEntityRepository extends JpaRepository<PromotionDbEntity, String>{
    @Query(value = "SELECT * FROM promotion p WHERE p.id_product = :idProduct AND :date BETWEEN p.start_date AND p.end_date", nativeQuery = true)
    Optional<PromotionDbEntity> findPromotionByProductAndDate(
        @Param("idProduct") String idProduct, 
        @Param("date") LocalDate date
    );
}
