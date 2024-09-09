package com.eatsleep.bill.bill.infrastructure.inputports;

import com.eatsleep.bill.billdescription.domain.BillDescription;
import java.util.List;

public interface GetTopRestaurantBillsInputPort {
    List<BillDescription> getTopRestaurantBills();    
}
