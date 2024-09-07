package com.eatsleep.hotel.maintenance.infrastructure.inputports;

import com.eatsleep.hotel.maintenance.domain.Maintenance;
import java.util.List;

public interface MakeMaintenanceAllRoomsInputPort {
    
    List<Maintenance> makeMaintenanceAllRooms();

}
