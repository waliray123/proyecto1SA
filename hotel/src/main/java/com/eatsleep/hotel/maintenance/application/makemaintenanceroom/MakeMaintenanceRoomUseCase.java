package com.eatsleep.hotel.maintenance.application.makemaintenanceroom;

import com.eatsleep.hotel.common.UseCase;
import com.eatsleep.hotel.maintenance.domain.Maintenance;
import com.eatsleep.hotel.maintenance.infrastructure.inputports.MakeMaintenanceRoomInputPort;
import com.eatsleep.hotel.maintenance.infrastructure.outputadapters.db.MaintenanceDbOutputAdapter;
import com.eatsleep.hotel.room.domain.Room;
import com.eatsleep.hotel.room.infrastructure.outputadapters.db.RoomDbOutputAdapter;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import java.time.LocalDate;
import java.util.UUID;
import org.springframework.beans.factory.annotation.Autowired;

@UseCase
@Transactional
public class MakeMaintenanceRoomUseCase implements MakeMaintenanceRoomInputPort {

    private RoomDbOutputAdapter roomOutputAdapter;
    private MaintenanceDbOutputAdapter maintenanceOutputAdapter;

    @Autowired
    public MakeMaintenanceRoomUseCase(RoomDbOutputAdapter roomOutputAdapter, MaintenanceDbOutputAdapter maintenanceOutputAdapter) {
        this.roomOutputAdapter = roomOutputAdapter;
        this.maintenanceOutputAdapter = maintenanceOutputAdapter;
    }
    
    @Override
    public Maintenance createMaintenanceRoom(String idRoom) {
        Room room = roomOutputAdapter.getRoomById(idRoom)
                .orElseThrow(() -> new EntityNotFoundException("Cuarto no encontrado con id: " + idRoom));

        // Aquí puedes crear y retornar la entidad `Maintenance`
        Maintenance maintenance = Maintenance.builder()
                .idMaintenance(UUID.randomUUID())
                .dateMaintenance(LocalDate.now())
                .maintenance(room.getMaintenance()) // o cualquier valor predeterminado
                .room(room)
                .build();

        // Guardar la entidad y retornarla
        maintenance = maintenanceOutputAdapter.makeMaintenanceRoom(maintenance);
        return maintenance;
    }

}
