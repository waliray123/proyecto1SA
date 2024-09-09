package com.eatsleep.bill.bill.application.getbilldescriptionbyidclientusecase;

import com.eatsleep.bill.bill.infrastructure.inputports.GetBillsDescriptionByIdClientInputPort;
import com.eatsleep.bill.common.UseCase;
import com.eatsleep.bill.bill.infrastructure.outputadapters.db.BillDbOutputAdapter;
import com.eatsleep.bill.billdescription.domain.BillDescription;
import com.eatsleep.bill.billdescription.infrastructure.outputadapters.db.BillDescriptionDbOutputAdapter;
import jakarta.transaction.Transactional;
import java.time.LocalDate;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;

@UseCase
@Transactional
public class GetBillDescriptionByIdClientUseCase implements GetBillsDescriptionByIdClientInputPort{
    
    private BillDbOutputAdapter billDbOutputAdapter;
    private BillDescriptionDbOutputAdapter billDescriptionDbOutputAdapter;

    @Autowired
    public GetBillDescriptionByIdClientUseCase(BillDbOutputAdapter billDbOutputAdapter
            , BillDescriptionDbOutputAdapter billDescriptionDbOutputAdapter) {
        this.billDbOutputAdapter = billDbOutputAdapter;
        this.billDescriptionDbOutputAdapter = billDescriptionDbOutputAdapter;
    }

    @Override
    public List<BillDescription> getBillsDescriptionByIdClient(String idClient,String idLocation, LocalDate startDate, LocalDate endDate) {
        if(idLocation == null){
            return this.billDescriptionDbOutputAdapter.getBillDescriptionsByIdClient(idClient,startDate,endDate);
        }
        return this.billDescriptionDbOutputAdapter.getBillDescriptionsByIdClientIdLocation(idClient,idLocation,startDate,endDate);
    }



    
    


}
