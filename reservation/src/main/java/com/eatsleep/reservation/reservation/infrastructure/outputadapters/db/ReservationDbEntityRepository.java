package com.eatsleep.reservation.reservation.infrastructure.outputadapters.db;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ReservationDbEntityRepository extends JpaRepository<ReservationDbEntity, String>{
    List<ReservationDbEntity> findAllByUserAndIdLocation(String userId, String idLocation);
}
