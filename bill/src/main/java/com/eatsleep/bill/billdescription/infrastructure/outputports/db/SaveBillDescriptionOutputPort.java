package com.eatsleep.bill.billdescription.infrastructure.outputports.db;

import com.eatsleep.bill.billdescription.domain.BillDescription;

public interface SaveBillDescriptionOutputPort {
    BillDescription saveBillDescription(BillDescription billDescription);
}
