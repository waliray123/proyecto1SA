package com.eatsleep.bill.billdescription.application.topdishusecase;

import com.eatsleep.bill.common.UseCase;
import com.eatsleep.bill.bill.infrastructure.inputports.GetTopDishBillsInputPort;
import com.eatsleep.bill.bill.infrastructure.inputports.GetTopRoomBillsInputPort;
import com.eatsleep.bill.billdescription.domain.BillDescription;
import com.eatsleep.bill.billdescription.infrastructure.outputadapters.db.BillDescriptionDbOutputAdapter;
import jakarta.transaction.Transactional;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;

@UseCase
@Transactional
public class GetTopDishUseCase implements GetTopDishBillsInputPort{
    
    private BillDescriptionDbOutputAdapter billDescriptionDbOutputAdapter;

    @Autowired
    public GetTopDishUseCase(BillDescriptionDbOutputAdapter billDescriptionDbOutputAdapter) {
        this.billDescriptionDbOutputAdapter = billDescriptionDbOutputAdapter;
    }

    @Override
    public List<BillDescription> getTopDishBills() {
        String idTopDish = this.billDescriptionDbOutputAdapter.findTopDishOutputPort();
        
        return this.billDescriptionDbOutputAdapter.getBillDescriptionsByIdDish(idTopDish);
    }

    @Override
    public List<BillDescription> getTopDishBillsByIdLocation(String idLocation) {
        String idTopDish = this.billDescriptionDbOutputAdapter.findTopDishByIdLocationOutputPort(idLocation);
        
        return this.billDescriptionDbOutputAdapter.getBillDescriptionsByIdDish(idTopDish);
    }

    
    

}
