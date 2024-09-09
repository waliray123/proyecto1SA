package com.eatsleep.hotel.hotel.infrastructure.inputadapters.restapi.response;

import com.eatsleep.hotel.maintenance.domain.Maintenance;
import java.time.LocalDate;
import lombok.Value;

@Value
public class MaintenanceResponse {
    String idMaintenance;
    LocalDate dateMaintenance;
    Double maintenanceCost;
    String roomId;

    public static MaintenanceResponse from(Maintenance maintenance) {
        return new MaintenanceResponse(
                maintenance.getIdMaintenance().toString(),
                maintenance.getDateMaintenance(),
                maintenance.getMaintenance(),
                maintenance.getRoom().getId().toString()
        );
    }
}
