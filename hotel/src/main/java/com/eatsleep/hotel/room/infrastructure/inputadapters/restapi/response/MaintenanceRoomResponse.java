package com.eatsleep.hotel.room.infrastructure.inputadapters.restapi.response;

import com.eatsleep.hotel.maintenance.domain.Maintenance;
import java.time.LocalDate;
import lombok.Value;

@Value
public class MaintenanceRoomResponse {

    private String idMaintenance;
    private LocalDate dateMaintenance;
    private Double maintenance;
    private String room; 

    public MaintenanceRoomResponse(Maintenance maintenance) {
        this.idMaintenance = maintenance.getIdMaintenance().toString();
        this.dateMaintenance = maintenance.getDateMaintenance();
        this.maintenance = maintenance.getMaintenance();
        this.room = maintenance.getRoom().getId().toString();
    }
    
}
