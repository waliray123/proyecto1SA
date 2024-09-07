package com.eatsleep.hotel.room.infrastructure.inputports;

import com.eatsleep.hotel.room.domain.Room;
import java.util.List;
import java.util.Optional;

public interface RetrieveRoomInputPort {
    Optional<Room> getRoomById(String id);
    List<Room> getAllRooms();
}
