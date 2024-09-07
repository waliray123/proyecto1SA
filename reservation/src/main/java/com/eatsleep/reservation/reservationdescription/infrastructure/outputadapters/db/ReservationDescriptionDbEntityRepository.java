package com.eatsleep.reservation.reservationdescription.infrastructure.outputadapters.db;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface ReservationDescriptionDbEntityRepository extends JpaRepository<ReservationDescriptionDbEntity, String>{
    @Query("SELECT rd FROM ReservationDescriptionDbEntity rd " +
       "JOIN rd.reservation r " +
       "WHERE rd.idProduct = :idRoom " +
       "AND :date BETWEEN r.dateStart AND r.dateEnd " +
       "AND r.status IN ('PENDING', 'CONFIRMED')")
    Optional<ReservationDescriptionDbEntity> findReservationByRoomAndDatePendingConfirmed(String idRoom, LocalDate date);
    
    // Consulta nativa para obtener todas las descripciones de la reserva por id de reserva
    @Query(value = "SELECT * FROM reservation_description WHERE reservation_id = :reservationId", nativeQuery = true)
    List<ReservationDescriptionDbEntity> findAllByReservationIdNative(@Param("reservationId") String reservationId);
}
