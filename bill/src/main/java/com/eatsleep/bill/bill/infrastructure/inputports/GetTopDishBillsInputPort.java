package com.eatsleep.bill.bill.infrastructure.inputports;

import com.eatsleep.bill.billdescription.domain.BillDescription;
import java.util.List;

public interface GetTopDishBillsInputPort {
    List<BillDescription> getTopDishBills();
    List<BillDescription> getTopDishBillsByIdLocation(String idLocation);
}
