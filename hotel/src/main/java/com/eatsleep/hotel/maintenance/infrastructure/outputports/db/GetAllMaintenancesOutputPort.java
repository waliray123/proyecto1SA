package com.eatsleep.hotel.maintenance.infrastructure.outputports.db;

import com.eatsleep.hotel.maintenance.domain.Maintenance;
import java.time.LocalDate;
import java.util.List;

public interface GetAllMaintenancesOutputPort {
    List<Maintenance> getAllMaintenance(LocalDate startDate, LocalDate endDate);
}
