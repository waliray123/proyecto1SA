package com.eatsleep.hotel.room.infrastructure.outputadapters.db;

import com.eatsleep.hotel.hotel.infrastructure.outputadapters.db.HotelDbEntity;
import com.eatsleep.hotel.room.domain.Room;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import java.util.UUID;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "room")
@Getter
@Setter
@NoArgsConstructor
public class RoomDbEntity {
    @Id
    @Column(name = "id_room", columnDefinition = "CHAR(36)")
    private String idRoom;  // Cambiar a String para UUID en formato de texto

    @Column(name = "number")
    private Integer number;

    @Column(name = "unit_price")
    private Double unitPrice;

    @Column(name = "floor")
    private Integer floor;

    @Column(name = "maintenance")
    private Double maintenance;

    @Column(name = "description", length = 200)
    private String description;
    
    @Column(name = "occupied", nullable = false)
    private boolean occupied;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "hotel_id_hotel", nullable = false, columnDefinition = "CHAR(36)")
    private HotelDbEntity hotel;
    
    public Room toDomainModel() {
        return Room.builder()
                .id(UUID.fromString(this.getIdRoom()))  // ID se maneja como String
                .number(this.getNumber())
                .unitPrice(this.getUnitPrice())
                .floor(this.getFloor())
                .maintenance(this.getMaintenance())
                .description(this.getDescription())
                .occupied(this.isOccupied())
                .hotel(this.getHotel().toDomainModel())
                .build();
    }
    
    public static RoomDbEntity from(Room room) {
        RoomDbEntity roomDbEntity = new RoomDbEntity();
        roomDbEntity.setIdRoom(room.getId() != null ? room.getId().toString() : UUID.randomUUID().toString());
        roomDbEntity.setNumber(room.getNumber());
        roomDbEntity.setUnitPrice(room.getUnitPrice());
        roomDbEntity.setFloor(room.getFloor());
        roomDbEntity.setMaintenance(room.getMaintenance());
        roomDbEntity.setDescription(room.getDescription());
        roomDbEntity.setOccupied(room.isOccupied());
        roomDbEntity.setHotel(HotelDbEntity.from(room.getHotel()));

        return roomDbEntity;
    }

}
