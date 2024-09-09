package com.eatsleep.bill.billdescription.infrastructure.outputports.db;

import com.eatsleep.bill.billdescription.domain.BillDescription;
import java.time.LocalDate;
import java.util.List;

public interface GetBillDescriptionsByIdClientOutputPort {
    List<BillDescription> getBillDescriptionsByIdClient(String idClient,LocalDate startDate, LocalDate endDate);
    List<BillDescription> getBillDescriptionsByIdClientIdLocation(String idClient,String idLocation,LocalDate startDate, LocalDate endDate);
}
