package com.eatsleep.hotel.maintenance.infrastructure.outputadapters.db;

import com.eatsleep.hotel.common.OutputAdapter;
import com.eatsleep.hotel.maintenance.domain.Maintenance;
import com.eatsleep.hotel.maintenance.infrastructure.outputports.db.GetAllMaintenancesOutputPort;
import com.eatsleep.hotel.maintenance.infrastructure.outputports.db.MakeMaintenanceRoomOutputPort;
import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;


@OutputAdapter
public class MaintenanceDbOutputAdapter implements MakeMaintenanceRoomOutputPort, GetAllMaintenancesOutputPort{
    
    private MaintenanceDbEntityRepository maintenanceDbEntityRepository;

    public MaintenanceDbOutputAdapter(MaintenanceDbEntityRepository maintenanceDbEntityRepository) {
        this.maintenanceDbEntityRepository = maintenanceDbEntityRepository;
    }

    @Override
    public Maintenance makeMaintenanceRoom(Maintenance maintenance) {
        MaintenanceDbEntity maintenanceEntity = MaintenanceDbEntity.from(maintenance);
        maintenanceEntity = maintenanceDbEntityRepository.save(maintenanceEntity);
        return maintenanceEntity.toDomainModel();
    }

    @Override
    public List<Maintenance> getAllMaintenance(LocalDate startDate, LocalDate endDate) {
        List<MaintenanceDbEntity> maintenanceDbEntities = this.maintenanceDbEntityRepository.findAllMaintenancesBetweenDates(startDate, endDate);
        return maintenanceDbEntities.stream()
                                    .map(MaintenanceDbEntity::toDomainModel)
                                    .collect(Collectors.toList());
    }
    
    

    

}
