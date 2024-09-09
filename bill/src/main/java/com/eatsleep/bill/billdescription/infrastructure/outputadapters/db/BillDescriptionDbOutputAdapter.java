package com.eatsleep.bill.billdescription.infrastructure.outputadapters.db;

import com.eatsleep.bill.common.OutputAdapter;
import com.eatsleep.bill.billdescription.domain.BillDescription;
import com.eatsleep.bill.billdescription.infrastructure.outputports.db.FindTopDishByLocationOutputPort;
import com.eatsleep.bill.billdescription.infrastructure.outputports.db.FindTopDishOutputPort;
import com.eatsleep.bill.billdescription.infrastructure.outputports.db.FindTopRoomByLocationOutputPort;
import com.eatsleep.bill.billdescription.infrastructure.outputports.db.FindTopRoomOutputPort;
import com.eatsleep.bill.billdescription.infrastructure.outputports.db.GetBillDescriptionsByIdBillOutputPort;
import com.eatsleep.bill.billdescription.infrastructure.outputports.db.GetBillDescriptionsByIdClientOutputPort;
import com.eatsleep.bill.billdescription.infrastructure.outputports.db.GetBillDescriptionsByIdDishOutputPort;
import com.eatsleep.bill.billdescription.infrastructure.outputports.db.GetBillDescriptionsByIdLocationOutputPort;
import com.eatsleep.bill.billdescription.infrastructure.outputports.db.GetBillDescriptionsByIdRoomOutputPort;
import com.eatsleep.bill.billdescription.infrastructure.outputports.db.SaveBillDescriptionOutputPort;
import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;

@OutputAdapter
public class BillDescriptionDbOutputAdapter implements SaveBillDescriptionOutputPort,FindTopRoomOutputPort
    ,FindTopDishOutputPort,FindTopRoomByLocationOutputPort,FindTopDishByLocationOutputPort
    ,GetBillDescriptionsByIdDishOutputPort,GetBillDescriptionsByIdRoomOutputPort
    ,GetBillDescriptionsByIdLocationOutputPort, GetBillDescriptionsByIdClientOutputPort
    ,GetBillDescriptionsByIdBillOutputPort{
    
    private BillDescriptionDbEntityRepository billDbEntityRepository;

    @Autowired
    public BillDescriptionDbOutputAdapter(BillDescriptionDbEntityRepository billDbEntityRepository) {
        this.billDbEntityRepository = billDbEntityRepository;
    }    

    @Override
    public BillDescription saveBillDescription(BillDescription billDescription) {
        BillDescriptionDbEntity billDescriptionDbEntity = BillDescriptionDbEntity.fromDomainModel(billDescription);
        return this.billDbEntityRepository.save(billDescriptionDbEntity).toDomainModel();
    }

    @Override
    public String findTopRoomOutputPort() {
        return this.billDbEntityRepository.findRoomWithMaxQuantity();
    }

    @Override
    public String findTopDishOutputPort() {
        return this.billDbEntityRepository.findDishWithMaxSale();
    }

    @Override
    public String findTopRoomByIdLocationOutputPort(String idLocation) {
        return this.billDbEntityRepository.findRoomWithMaxQuantityByLocation(idLocation);
    }

    @Override
    public String findTopDishByIdLocationOutputPort(String idLocation) {
        return this.billDbEntityRepository.findDishWithMaxSalesByLocation(idLocation);
    }

    @Override
    public List<BillDescription> getBillDescriptionsByIdDish(String idDish) {
        return this.billDbEntityRepository.findAllByDishId(idDish).stream()
                .map(billDescriptionDbEntity -> billDescriptionDbEntity.toDomainModel())
                .collect(Collectors.toList());
    }

    @Override
    public List<BillDescription> getBillDescriptionsByIdRoom(String idRoom) {
        return this.billDbEntityRepository.findAllByRoomId(idRoom).stream()
                .map(billDescriptionDbEntity -> billDescriptionDbEntity.toDomainModel())
                .collect(Collectors.toList());
    }

    @Override
    public List<BillDescription> getBillDescriptionsByIdLocationByDates(String idLocation, LocalDate startDate, LocalDate endDate) {
        if(startDate == null || endDate == null ){
            return this.billDbEntityRepository.getBillDescriptionsByIdLocationAllTime(idLocation).stream()
                .map(billDescriptionDbEntity -> billDescriptionDbEntity.toDomainModel())
                .collect(Collectors.toList());
        }
        
        return this.billDbEntityRepository.getBillDescriptionsByIdLocationByDates(idLocation,startDate,endDate).stream()
                .map(billDescriptionDbEntity -> billDescriptionDbEntity.toDomainModel())
                .collect(Collectors.toList());
    }

    @Override
    public List<BillDescription> getBillDescriptionsByIdClient(String idClient,LocalDate startDate, LocalDate endDate) {
        return this.billDbEntityRepository.getBillDescriptionsByIdClient(idClient,startDate,endDate).stream()
                .map(billDescriptionDbEntity -> billDescriptionDbEntity.toDomainModel())
                .collect(Collectors.toList());
    }

    @Override
    public List<BillDescription> getBillDescriptionsByIdClientIdLocation(String idClient, String idLocation,LocalDate startDate, LocalDate endDate) {
        return this.billDbEntityRepository.getBillDescriptionsByIdClientAndIdLocation(idClient,idLocation,startDate,endDate).stream()
                .map(billDescriptionDbEntity -> billDescriptionDbEntity.toDomainModel())
                .collect(Collectors.toList());
    }

    @Override
    public List<BillDescription> getBillDescriptionsByIdBill(String idBill) {
        return this.billDbEntityRepository.findBillDescriptionsByIdBill(idBill).stream()
                .map(billDescriptionDbEntity -> billDescriptionDbEntity.toDomainModel())
                .collect(Collectors.toList());
    }
    
    


}
