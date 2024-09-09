package com.eatsleep.hotel.maintenance.application.getallmaintenancesusecase;

import com.eatsleep.hotel.common.UseCase;
import com.eatsleep.hotel.hotel.infrastructure.inputports.GetAllMaintenancesInputPort;
import com.eatsleep.hotel.maintenance.domain.Maintenance;
import com.eatsleep.hotel.maintenance.infrastructure.outputadapters.db.MaintenanceDbOutputAdapter;
import jakarta.transaction.Transactional;
import java.time.LocalDate;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;

@UseCase
@Transactional
public class GetAllMaintenancesUseCase implements GetAllMaintenancesInputPort{
    
    private MaintenanceDbOutputAdapter maintenanceOutputAdapter;

    @Autowired
    public GetAllMaintenancesUseCase(MaintenanceDbOutputAdapter maintenanceOutputAdapter) {
        this.maintenanceOutputAdapter = maintenanceOutputAdapter;
    }
    
    @Override
    public List<Maintenance> getAllMaintenance(LocalDate startDate, LocalDate endDate) {
        return this.maintenanceOutputAdapter.getAllMaintenance(startDate, endDate);
    }

}
