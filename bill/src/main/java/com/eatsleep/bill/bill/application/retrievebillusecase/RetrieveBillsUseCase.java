package com.eatsleep.bill.bill.application.retrievebillusecase;

import com.eatsleep.bill.bill.domain.Bill;
import com.eatsleep.bill.bill.infrastructure.inputports.RetrieveBillsInputPort;
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
public class RetrieveBillsUseCase implements RetrieveBillsInputPort{
    
    private BillDbOutputAdapter billDbOutputAdapter;
    private BillDescriptionDbOutputAdapter billDescriptionDbOutputAdapter;

    @Autowired
    public RetrieveBillsUseCase(BillDbOutputAdapter billDbOutputAdapter
            , BillDescriptionDbOutputAdapter billDescriptionDbOutputAdapter) {
        this.billDbOutputAdapter = billDbOutputAdapter;
        this.billDescriptionDbOutputAdapter = billDescriptionDbOutputAdapter;
    }

    @Override
    public List<Bill> getAllBillsByDate(LocalDate startDate, LocalDate endDate) {
        List<Bill> bills = this.billDbOutputAdapter.getAllBillsByDate(startDate, endDate);
        for (Bill bill : bills) {
            List<BillDescription> billsDescription = this.billDescriptionDbOutputAdapter.getBillDescriptionsByIdBill(bill.getId().toString());
            bill.setDescriptions(billsDescription);
        }
        
        return bills;
    }



    
    


}
