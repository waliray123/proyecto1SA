package com.eatsleep.hotel.maintenance.application.makemaintenanceallrooms;

import com.eatsleep.hotel.common.UseCase;
import com.eatsleep.hotel.maintenance.domain.Maintenance;
import com.eatsleep.hotel.maintenance.infrastructure.inputports.MakeMaintenanceAllRoomsInputPort;
import com.eatsleep.hotel.maintenance.infrastructure.outputadapters.db.MaintenanceDbOutputAdapter;
import com.eatsleep.hotel.room.domain.Room;
import com.eatsleep.hotel.room.infrastructure.outputadapters.db.RoomDbOutputAdapter;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import org.springframework.beans.factory.annotation.Autowired;

@UseCase
@Transactional
public class MakeMaintenanceAllRoomsUseCase implements MakeMaintenanceAllRoomsInputPort {

    private RoomDbOutputAdapter roomOutputAdapter;
    private MaintenanceDbOutputAdapter maintenanceOutputAdapter;

    @Autowired
    public MakeMaintenanceAllRoomsUseCase(RoomDbOutputAdapter roomOutputAdapter, MaintenanceDbOutputAdapter maintenanceOutputAdapter) {
        this.roomOutputAdapter = roomOutputAdapter;
        this.maintenanceOutputAdapter = maintenanceOutputAdapter;
    }
    
    @Override
    public List<Maintenance> makeMaintenanceAllRooms() {
        List<Room> rooms = roomOutputAdapter.getAllRooms();

        List<Maintenance> maintenances = new ArrayList<>();
        
        for (Room room : rooms) {
            Maintenance maintenance = Maintenance.builder()
                .idMaintenance(UUID.randomUUID())
                .dateMaintenance(LocalDate.now())
                .maintenance(room.getMaintenance()) // o cualquier valor predeterminado
                .room(room)
                .build();
            maintenance = maintenanceOutputAdapter.makeMaintenanceRoom(maintenance);
            maintenances.add(maintenance);
        }
        
        return maintenances;
    }

}
