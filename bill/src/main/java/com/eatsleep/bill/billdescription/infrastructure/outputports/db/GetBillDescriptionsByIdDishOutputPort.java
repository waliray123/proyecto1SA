package com.eatsleep.bill.billdescription.infrastructure.outputports.db;

import com.eatsleep.bill.billdescription.domain.BillDescription;
import java.util.List;

public interface GetBillDescriptionsByIdDishOutputPort {
    List<BillDescription> getBillDescriptionsByIdDish(String idDish);
}
