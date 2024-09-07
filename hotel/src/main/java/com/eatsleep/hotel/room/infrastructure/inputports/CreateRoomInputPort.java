package com.eatsleep.hotel.room.infrastructure.inputports;

import com.eatsleep.hotel.room.application.createroomusecase.CreateRoomRequest;
import com.eatsleep.hotel.room.domain.Room;

public interface CreateRoomInputPort {
    
    Room createRoom(CreateRoomRequest room);

}
