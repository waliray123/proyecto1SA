package com.eatsleep.hotel.room.application.checkexistroomusecase;

import com.eatsleep.hotel.common.UseCase;
import com.eatsleep.hotel.room.domain.Room;
import com.eatsleep.hotel.room.infrastructure.inputports.ExistRoomInputPort;
import com.eatsleep.hotel.room.infrastructure.outputadapters.db.RoomDbOutputAdapter;
import jakarta.transaction.Transactional;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;

@UseCase
@Transactional
public class CheckExistRoomUseCase implements ExistRoomInputPort{
    
    private RoomDbOutputAdapter roomOutputAdapter;

    @Autowired
    public CheckExistRoomUseCase(RoomDbOutputAdapter roomOutputAdapter) {
        this.roomOutputAdapter = roomOutputAdapter;
    }

    @Override
    public boolean checkExistRoom(String idRoom) {
        Optional<Room> hotelOptional = roomOutputAdapter.getRoomById(idRoom);
        if(hotelOptional.isPresent()){
            return true;
        }
        return false;
    }

}
