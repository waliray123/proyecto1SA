package com.eatsleep.hotel.room.application.updateroomusecase;

import com.eatsleep.hotel.room.infrastructure.inputadapters.restapi.response.UpdateRoomResponse;
import com.eatsleep.hotel.common.UseCase;
import com.eatsleep.hotel.hotel.domain.Hotel;
import com.eatsleep.hotel.hotel.infrastructure.outputadapters.db.HotelDbEntity;
import com.eatsleep.hotel.hotel.infrastructure.outputadapters.db.HotelDbOutputAdapter;
import com.eatsleep.hotel.room.domain.Room;
import com.eatsleep.hotel.room.infrastructure.inputports.UpdateRoomInputPort;
import com.eatsleep.hotel.room.infrastructure.outputadapters.db.RoomDbEntity;
import com.eatsleep.hotel.room.infrastructure.outputadapters.db.RoomDbOutputAdapter;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;

@UseCase
@Transactional
public class UpdateRoomUseCase implements UpdateRoomInputPort{

    
    private RoomDbOutputAdapter roomOutputAdapter;
    private HotelDbOutputAdapter hotelOutputAdapter;

    @Autowired
    public UpdateRoomUseCase(RoomDbOutputAdapter roomOutputAdapter, HotelDbOutputAdapter hotelOutputAdapter) {
        this.roomOutputAdapter = roomOutputAdapter;
        this.hotelOutputAdapter = hotelOutputAdapter;
    }
    
    @Override
    public Optional<Room> updateRoom(String idRoom, UpdateRoomRequest roomRequest) {
        // Validar la información de la habitación
        if (roomRequest.getNumber() <= 0) {
            throw new IllegalArgumentException("El número de la habitación debe ser mayor que cero.");
        }
        if (roomRequest.getUnitPrice() <= 0) {
            throw new IllegalArgumentException("El precio de la habitación debe ser mayor que cero.");
        }
        if (roomRequest.getFloor() < 0) {
            throw new IllegalArgumentException("El piso de la habitación no puede ser negativo.");
        }
        if (roomRequest.getIdHotel() == null) {
            throw new IllegalArgumentException("El ID del hotel no puede estar vacío.");
        }
        if (roomRequest.getMaintenance() <= 0) {
            throw new IllegalArgumentException("El mantenimiento de la habitación debe ser mayor que cero.");
        }
        if (roomRequest.getDescription() == null) {
            throw new IllegalArgumentException("Tiene que existir una descripcion de la habitación.");
        }
        
        //Traer el hotel y sino existe retornar error
        Hotel hotel = this.hotelOutputAdapter.getHotelById(roomRequest.getIdHotel())
                .orElseThrow(() -> new EntityNotFoundException("El hotel no existe, ingrese un id correcto"));
        
        // Buscar el cuarto existente en la base de datos
        Room existingRoomOptional = roomOutputAdapter.getRoomById(idRoom)
                .orElseThrow(() -> new EntityNotFoundException("El cuarto no existe, ingrese un id correcto"));

        // Actualizar los campos del cuarto existente
        Room room = Room.builder()
                .id(existingRoomOptional.getId())
            .number(roomRequest.getNumber())
            .unitPrice(roomRequest.getUnitPrice())
            .floor(roomRequest.getFloor())
            .maintenance(roomRequest.getMaintenance())
            .description(roomRequest.getDescription())
            .occupied(false)
            .hotel(hotel)
            .build();

        // Guardar el cuarto actualizado
        Optional<Room> updatedRoomOptional = roomOutputAdapter.updateRoom(idRoom, room);

        // Crear y devolver la respuesta        
        return updatedRoomOptional;
    }

}
