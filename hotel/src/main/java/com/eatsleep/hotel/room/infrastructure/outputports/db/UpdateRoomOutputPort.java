package com.eatsleep.hotel.room.infrastructure.outputports.db;

import com.eatsleep.hotel.room.domain.Room;
import java.util.Optional;

public interface UpdateRoomOutputPort {
    Optional<Room> updateRoom(String id, Room updatedRoom);
}
