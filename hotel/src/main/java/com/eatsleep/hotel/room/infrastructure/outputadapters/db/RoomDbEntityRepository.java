package com.eatsleep.hotel.room.infrastructure.outputadapters.db;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface RoomDbEntityRepository extends JpaRepository<RoomDbEntity, String>{
    @Query(value = "SELECT * FROM room WHERE number = :number AND hotel_id_hotel = :hotelId", nativeQuery = true)
    Optional<RoomDbEntity> findByNumberAndHotelId(@Param("number") int number, @Param("hotelId") String hotelId);
}
