package com.eatsleep.hotel.room.infrastructure.outputports.db;

import com.eatsleep.hotel.room.domain.Room;
import java.util.List;
import java.util.Optional;

public interface RetrieveRoomOutputPort {
    Optional<Room> getRoomById(String id);
    List<Room> getAllRooms();
    Optional<Room> findRoomByNumberAndHotelId(int number, String hotelId);
}
