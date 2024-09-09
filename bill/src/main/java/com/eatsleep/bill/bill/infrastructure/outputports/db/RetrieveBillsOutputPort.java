package com.eatsleep.bill.bill.infrastructure.outputports.db;

import com.eatsleep.bill.bill.domain.Bill;
import java.time.LocalDate;
import java.util.List;

public interface RetrieveBillsOutputPort {
    List<Bill> getAllBillsByDate(LocalDate startDate,LocalDate endDate);
}
