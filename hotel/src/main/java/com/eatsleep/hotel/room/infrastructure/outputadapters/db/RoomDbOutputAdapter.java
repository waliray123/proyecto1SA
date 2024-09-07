package com.eatsleep.hotel.room.infrastructure.outputadapters.db;

import com.eatsleep.hotel.common.OutputAdapter;
import com.eatsleep.hotel.room.domain.Room;
import com.eatsleep.hotel.room.infrastructure.outputports.db.CreateRoomOutputPort;
import com.eatsleep.hotel.room.infrastructure.outputports.db.RetrieveRoomOutputPort;
import com.eatsleep.hotel.room.infrastructure.outputports.db.UpdateRoomOutputPort;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@OutputAdapter
public class RoomDbOutputAdapter implements CreateRoomOutputPort,UpdateRoomOutputPort,RetrieveRoomOutputPort{
    
    private RoomDbEntityRepository roomDbEntityRepository;

    public RoomDbOutputAdapter(RoomDbEntityRepository roomDbEntityRepository) {
        this.roomDbEntityRepository = roomDbEntityRepository;
    }

    @Override
    public Optional<Room> updateRoom(String id, Room updatedRoom) {

        if (roomDbEntityRepository.existsById(id)) {
            RoomDbEntity roomDbEntity = RoomDbEntity.from(updatedRoom);

            RoomDbEntity savedEntity = roomDbEntityRepository.save(roomDbEntity);
            return Optional.of(savedEntity.toDomainModel());
        }

        return Optional.empty();
    }

    @Override
    public Optional<Room> getRoomById(String id) {
        Optional<RoomDbEntity> roomDbEntityOptional = roomDbEntityRepository.findById(id);

        // Mapeo de RoomDbEntity a Room
        return roomDbEntityOptional.map(RoomDbEntity::toDomainModel);
    }

    @Override
    public List<Room> getAllRooms() {
        // Obtener todas las entidades RoomDbEntity
        List<RoomDbEntity> roomDbEntities = roomDbEntityRepository.findAll();

        // Convertir cada RoomDbEntity a Room utilizando el método toDomainModel
        return roomDbEntities.stream()
                .map(RoomDbEntity::toDomainModel)
                .collect(Collectors.toList());
    }

    @Override
    public Optional<Room> findRoomByNumberAndHotelId(int number, String hotelId) {
        Optional<RoomDbEntity> roomEntity = roomDbEntityRepository.findByNumberAndHotelId(number, hotelId);
        return roomEntity.map(RoomDbEntity::toDomainModel);
    }

    @Override
    public Room createRoom(Room room) {
        RoomDbEntity roomEntity = RoomDbEntity.from(room);
        roomEntity = roomDbEntityRepository.save(roomEntity);
        return roomEntity.toDomainModel();
    }

}
