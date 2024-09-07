package com.eatsleep.hotel.room.application.retrieveroomusecase;

import com.eatsleep.hotel.common.UseCase;
import com.eatsleep.hotel.room.domain.Room;
import com.eatsleep.hotel.room.infrastructure.inputports.RetrieveRoomInputPort;
import com.eatsleep.hotel.room.infrastructure.outputadapters.db.RoomDbOutputAdapter;
import jakarta.transaction.Transactional;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;

@UseCase
@Transactional
public class RetrieveRoomUseCase implements RetrieveRoomInputPort{
    
    private RoomDbOutputAdapter roomOutputAdapter;

    @Autowired
    public RetrieveRoomUseCase(RoomDbOutputAdapter roomOutputAdapter) {
        this.roomOutputAdapter = roomOutputAdapter;
    }

    @Override
    public Optional<Room> getRoomById(String id) {
        // Recuperar la habitación por ID
        Optional<Room> roomDbEntityOptional = roomOutputAdapter.getRoomById(id);
        
        // Convertir y devolver la respuesta, si la habitación fue encontrada
        return roomDbEntityOptional;
    }

    @Override
    public List<Room> getAllRooms() {
        // Recuperar todas las habitaciones
        List<Room> rooms = roomOutputAdapter.getAllRooms();
        
        // Convertir la lista de entidades a respuestas
        return rooms;
    }

}
