package com.eatsleep.reports.infrastructure.outputports.restapi;

import com.eatsleep.reports.application.allearningscostsusecase.MaintenanceResponse;
import java.time.LocalDate;
import java.util.List;

public interface FindAllMaintenancesOutputPort {
    List<MaintenanceResponse> findAllMaintenances(LocalDate startDate, LocalDate endDate);
}
