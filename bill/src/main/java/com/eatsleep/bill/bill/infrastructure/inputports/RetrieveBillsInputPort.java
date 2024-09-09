package com.eatsleep.bill.bill.infrastructure.inputports;

import com.eatsleep.bill.bill.domain.Bill;
import java.time.LocalDate;
import java.util.List;

public interface RetrieveBillsInputPort {
    List<Bill> getAllBillsByDate(LocalDate startDate,LocalDate endDate);
}
