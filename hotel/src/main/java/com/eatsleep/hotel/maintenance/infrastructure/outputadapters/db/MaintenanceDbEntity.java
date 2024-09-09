package com.eatsleep.hotel.maintenance.infrastructure.outputadapters.db;

import com.eatsleep.hotel.maintenance.domain.Maintenance;
import com.eatsleep.hotel.room.infrastructure.outputadapters.db.RoomDbEntity;
import jakarta.persistence.*;
import java.time.LocalDate;
import java.util.UUID;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "maintenance")
public class MaintenanceDbEntity {

    @Id
    @Column(name = "id_maintenance", nullable = false, columnDefinition = "CHAR(36)")
    private String idMaintenance;

    @Column(name = "date_maintenance", nullable = false)
    private LocalDate dateMaintenance;

    @Column(name = "maintenance", nullable = false)
    private Double maintenanceCost;

    @ManyToOne
    @JoinColumn(name = "room_id_room", nullable = false)
    private RoomDbEntity room;

    public Maintenance toDomainModel() {
        return Maintenance.builder()
                .idMaintenance(UUID.fromString(this.idMaintenance))
                .dateMaintenance(this.dateMaintenance)
                .maintenance(this.maintenanceCost)
                .room(this.room != null ? this.room.toDomainModel() : null)
                .build();
    }

    public static MaintenanceDbEntity from(Maintenance maintenance) {
        MaintenanceDbEntity entity = new MaintenanceDbEntity();
        entity.setIdMaintenance(
                maintenance.getIdMaintenance() != null ? maintenance.getIdMaintenance().toString() : UUID.randomUUID().toString());
        entity.setDateMaintenance(maintenance.getDateMaintenance());
        entity.setMaintenanceCost(maintenance.getMaintenance());
        entity.setRoom(RoomDbEntity.from(maintenance.getRoom()));
        return entity;
    }
}