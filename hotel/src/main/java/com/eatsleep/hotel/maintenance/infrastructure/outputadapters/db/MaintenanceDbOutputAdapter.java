package com.eatsleep.hotel.maintenance.infrastructure.outputadapters.db;

import com.eatsleep.hotel.common.OutputAdapter;
import com.eatsleep.hotel.maintenance.domain.Maintenance;
import com.eatsleep.hotel.maintenance.infrastructure.outputports.db.MakeMaintenanceRoomOutputPort;


@OutputAdapter
public class MaintenanceDbOutputAdapter implements MakeMaintenanceRoomOutputPort{
    
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
    
    

    

}
