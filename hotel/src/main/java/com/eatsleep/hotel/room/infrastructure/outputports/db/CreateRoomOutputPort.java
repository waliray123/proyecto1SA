package com.eatsleep.hotel.room.infrastructure.outputports.db;

import com.eatsleep.hotel.room.domain.Room;

public interface CreateRoomOutputPort {
    Room createRoom(Room room);
}
