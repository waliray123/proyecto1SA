package com.eatsleep.hotel.maintenance.infrastructure.outputports.db;

import com.eatsleep.hotel.maintenance.domain.Maintenance;
import com.eatsleep.hotel.room.domain.Room;

public interface MakeMaintenanceRoomOutputPort {
    Maintenance makeMaintenanceRoom(Maintenance maintenance);
}
