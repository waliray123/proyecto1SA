package com.eatsleep.bill.billdescription.infrastructure.outputadapters.db;

import com.eatsleep.bill.common.OutputAdapter;
import com.eatsleep.bill.billdescription.domain.BillDescription;
import com.eatsleep.bill.billdescription.infrastructure.outputports.db.SaveBillDescriptionOutputPort;
import org.springframework.beans.factory.annotation.Autowired;

@OutputAdapter
public class BillDescriptionDbOutputAdapter implements SaveBillDescriptionOutputPort {
    
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


}
