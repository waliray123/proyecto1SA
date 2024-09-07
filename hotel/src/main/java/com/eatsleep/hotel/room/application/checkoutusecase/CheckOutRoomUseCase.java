package com.eatsleep.hotel.room.application.checkoutusecase;

import com.eatsleep.hotel.common.UseCase;
import com.eatsleep.hotel.hotel.infrastructure.outputadapters.db.HotelDbOutputAdapter;
import com.eatsleep.hotel.room.domain.Room;
import com.eatsleep.hotel.room.infrastructure.inputports.CheckOutRoomInputPort;
import com.eatsleep.hotel.room.infrastructure.outputadapters.db.RoomDbOutputAdapter;
import jakarta.transaction.Transactional;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;

@UseCase
@Transactional
public class CheckOutRoomUseCase implements CheckOutRoomInputPort{
    
    private RoomDbOutputAdapter roomOutputAdapter;
    private HotelDbOutputAdapter hotelOutputAdapter;

    @Autowired
    public CheckOutRoomUseCase(RoomDbOutputAdapter roomOutputAdapter, HotelDbOutputAdapter hotelOutputAdapter) {
        this.roomOutputAdapter = roomOutputAdapter;
        this.hotelOutputAdapter = hotelOutputAdapter;
    }

    @Override
    public boolean checkOutRoom(String idRoom) {
        Optional<Room> room = this.roomOutputAdapter.getRoomById(idRoom);
        if(room.isEmpty() || room == null){
            return false;
        }
        room.get().setOccupied(false);
        this.roomOutputAdapter.updateRoom(idRoom, room.get());
        return true;
    }

}
