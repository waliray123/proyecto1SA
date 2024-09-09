package com.eatsleep.bill.billdescription.application.toproomusecase;

import com.eatsleep.bill.common.UseCase;
import com.eatsleep.bill.bill.infrastructure.inputports.GetTopRoomBillsInputPort;
import com.eatsleep.bill.billdescription.domain.BillDescription;
import com.eatsleep.bill.billdescription.infrastructure.outputadapters.db.BillDescriptionDbOutputAdapter;
import jakarta.transaction.Transactional;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;

@UseCase
@Transactional
public class GetTopRoomUseCase implements GetTopRoomBillsInputPort{
    
    private BillDescriptionDbOutputAdapter billDescriptionDbOutputAdapter;

    @Autowired
    public GetTopRoomUseCase(BillDescriptionDbOutputAdapter billDescriptionDbOutputAdapter) {
        this.billDescriptionDbOutputAdapter = billDescriptionDbOutputAdapter;
    }

    @Override
    public List<BillDescription> getTopRoomBills() {
        String idTopRoom = this.billDescriptionDbOutputAdapter.findTopRoomOutputPort();
        
        return this.billDescriptionDbOutputAdapter.getBillDescriptionsByIdRoom(idTopRoom);
    }

    @Override
    public List<BillDescription> getTopRoomBillsByIdLocation(String idLocation) {
        String idTopRoom = this.billDescriptionDbOutputAdapter.findTopRoomByIdLocationOutputPort(idLocation);
        
        return this.billDescriptionDbOutputAdapter.getBillDescriptionsByIdRoom(idTopRoom);
    }

    
    

}
