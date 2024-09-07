package com.eatsleep.hotel.room.application.createroomusecase;

import com.eatsleep.hotel.common.UseCase;
import com.eatsleep.hotel.hotel.domain.Hotel;
import com.eatsleep.hotel.hotel.infrastructure.outputadapters.db.HotelDbOutputAdapter;
import com.eatsleep.hotel.room.domain.Room;
import com.eatsleep.hotel.room.infrastructure.inputports.CreateRoomInputPort;
import com.eatsleep.hotel.room.infrastructure.outputadapters.db.RoomDbOutputAdapter;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;

@UseCase
@Transactional
public class CreateRoomUseCase implements CreateRoomInputPort{
    
    private RoomDbOutputAdapter roomOutputAdapter;
    private HotelDbOutputAdapter hotelOutputAdapter;

    @Autowired
    public CreateRoomUseCase(RoomDbOutputAdapter roomOutputAdapter, HotelDbOutputAdapter hotelOutputAdapter) {
        this.roomOutputAdapter = roomOutputAdapter;
        this.hotelOutputAdapter = hotelOutputAdapter;
    }

    @Override
    public Room createRoom(CreateRoomRequest roomRequest) {
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
        
        //Validar que en un hotel no existe un cuarto con ese numero
        boolean roomExists = roomOutputAdapter.findRoomByNumberAndHotelId(roomRequest.getNumber(), roomRequest.getIdHotel()).isPresent();
        if (roomExists) {
            throw new IllegalArgumentException("Ya existe una habitación con el número proporcionado en este hotel.");
        }
        
        
        // Convertir la entidad de dominio a una entidad de base de datos
        Room room = Room.builder()
            .number(roomRequest.getNumber())
            .unitPrice(roomRequest.getUnitPrice())
            .floor(roomRequest.getFloor())
            .maintenance(roomRequest.getMaintenance())
            .description(roomRequest.getDescription())
            .occupied(false)
            .hotel(hotel)  // Relación con la entidad de dominio Hotel
            .build();

        // Persistir la habitación en la base de datos usando el Output Adapter
        Room savedRoom = roomOutputAdapter.createRoom(room);
        
        // Crear y devolver la respuesta
        return savedRoom;
    }

}
