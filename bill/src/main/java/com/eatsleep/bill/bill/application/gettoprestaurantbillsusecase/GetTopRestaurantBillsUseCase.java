package com.eatsleep.bill.bill.application.gettoprestaurantbillsusecase;

import com.eatsleep.bill.bill.infrastructure.inputports.GetTopRestaurantBillsInputPort;
import com.eatsleep.bill.common.UseCase;
import com.eatsleep.bill.bill.infrastructure.outputadapters.db.BillDbOutputAdapter;
import com.eatsleep.bill.billdescription.domain.BillDescription;
import com.eatsleep.bill.billdescription.infrastructure.outputadapters.db.BillDescriptionDbOutputAdapter;
import jakarta.transaction.Transactional;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;

@UseCase
@Transactional
public class GetTopRestaurantBillsUseCase implements GetTopRestaurantBillsInputPort{
    
    private BillDbOutputAdapter billDbOutputAdapter;
    private BillDescriptionDbOutputAdapter billDescriptionDbOutputAdapter;

    @Autowired
    public GetTopRestaurantBillsUseCase(BillDbOutputAdapter billDbOutputAdapter
            , BillDescriptionDbOutputAdapter billDescriptionDbOutputAdapter) {
        this.billDbOutputAdapter = billDbOutputAdapter;
        this.billDescriptionDbOutputAdapter = billDescriptionDbOutputAdapter;
    }

    @Override
    public List<BillDescription> getTopRestaurantBills() {
        String topRestaurant = this.billDbOutputAdapter.findTopRestaurant();
        
        return this.billDescriptionDbOutputAdapter.getBillDescriptionsByIdLocationByDates(topRestaurant, null, null);
    }

    
    


}
