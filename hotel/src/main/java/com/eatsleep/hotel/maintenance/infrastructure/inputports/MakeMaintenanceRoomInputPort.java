package com.eatsleep.hotel.maintenance.infrastructure.inputports;

import com.eatsleep.hotel.maintenance.domain.Maintenance;

public interface MakeMaintenanceRoomInputPort {
    
    Maintenance createMaintenanceRoom(String idRoom);

}
