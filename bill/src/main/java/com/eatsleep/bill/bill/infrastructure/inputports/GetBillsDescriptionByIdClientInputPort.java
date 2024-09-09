package com.eatsleep.bill.bill.infrastructure.inputports;

import com.eatsleep.bill.billdescription.domain.BillDescription;
import java.time.LocalDate;
import java.util.List;

public interface GetBillsDescriptionByIdClientInputPort {
    List<BillDescription> getBillsDescriptionByIdClient(String idClient,String idLocation, LocalDate startDate, LocalDate endDate);    
}
