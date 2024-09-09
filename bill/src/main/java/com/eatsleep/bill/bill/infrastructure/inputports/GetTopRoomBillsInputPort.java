package com.eatsleep.bill.bill.infrastructure.inputports;

import com.eatsleep.bill.billdescription.domain.BillDescription;
import java.util.List;

public interface GetTopRoomBillsInputPort {
    List<BillDescription> getTopRoomBills();
    List<BillDescription> getTopRoomBillsByIdLocation(String idLocation);
}
