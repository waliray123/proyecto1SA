package com.eatsleep.reports.application.allearningscostsusecase;

import java.time.LocalDate;
import lombok.Value;

@Value
public class MaintenanceResponse {
    String idMaintenance;
    LocalDate dateMaintenance;
    Double maintenanceCost;
    String roomId;
}
