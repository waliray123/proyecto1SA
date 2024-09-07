package com.eatsleep.hotel.room.infrastructure.inputports;

import com.eatsleep.hotel.room.application.updateroomusecase.UpdateRoomRequest;
import com.eatsleep.hotel.room.domain.Room;
import java.util.Optional;

public interface UpdateRoomInputPort {
    Optional<Room> updateRoom(String idRoom, UpdateRoomRequest room);
}
