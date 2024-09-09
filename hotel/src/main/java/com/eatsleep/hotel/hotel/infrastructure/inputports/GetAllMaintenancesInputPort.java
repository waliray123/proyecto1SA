package com.eatsleep.hotel.hotel.infrastructure.inputports;

import com.eatsleep.hotel.maintenance.domain.Maintenance;
import java.time.LocalDate;
import java.util.List;

public interface GetAllMaintenancesInputPort {
    List<Maintenance> getAllMaintenance(LocalDate startDate, LocalDate endDate);
}
