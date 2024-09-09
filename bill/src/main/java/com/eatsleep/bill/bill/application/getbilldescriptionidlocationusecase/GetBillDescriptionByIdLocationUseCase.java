package com.eatsleep.bill.bill.application.getbilldescriptionidlocationusecase;

import com.eatsleep.bill.bill.infrastructure.inputports.GetBillsDescriptionByIdLocationInputPort;
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
public class GetBillDescriptionByIdLocationUseCase implements GetBillsDescriptionByIdLocationInputPort{
    
    private BillDbOutputAdapter billDbOutputAdapter;
    private BillDescriptionDbOutputAdapter billDescriptionDbOutputAdapter;

    @Autowired
    public GetBillDescriptionByIdLocationUseCase(BillDbOutputAdapter billDbOutputAdapter
            , BillDescriptionDbOutputAdapter billDescriptionDbOutputAdapter) {
        this.billDbOutputAdapter = billDbOutputAdapter;
        this.billDescriptionDbOutputAdapter = billDescriptionDbOutputAdapter;
    }


    @Override
    public List<BillDescription> getBillsDescriptionByIdLocation(String idLocation, LocalDate startDate, LocalDate endDate) {
        return this.billDescriptionDbOutputAdapter.getBillDescriptionsByIdLocationByDates(idLocation, startDate, endDate);
    }

    
    


}
