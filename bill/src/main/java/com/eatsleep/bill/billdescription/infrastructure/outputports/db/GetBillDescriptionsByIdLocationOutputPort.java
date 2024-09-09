package com.eatsleep.bill.billdescription.infrastructure.outputports.db;

import com.eatsleep.bill.billdescription.domain.BillDescription;
import java.time.LocalDate;
import java.util.List;

public interface GetBillDescriptionsByIdLocationOutputPort {
    List<BillDescription> getBillDescriptionsByIdLocationByDates(String idLocation, LocalDate startDate, LocalDate endDate);
}
