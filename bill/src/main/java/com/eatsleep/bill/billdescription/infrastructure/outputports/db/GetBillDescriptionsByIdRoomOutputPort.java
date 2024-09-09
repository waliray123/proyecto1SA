package com.eatsleep.bill.billdescription.infrastructure.outputports.db;

import com.eatsleep.bill.billdescription.domain.BillDescription;
import java.util.List;

public interface GetBillDescriptionsByIdRoomOutputPort {
    List<BillDescription> getBillDescriptionsByIdRoom(String idRoom);
}
