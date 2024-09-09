package com.eatsleep.reports.infrastructure.outputports.restapi;

import com.eatsleep.reports.application.allearningscostsusecase.BillResponse;
import java.time.LocalDate;
import java.util.List;

public interface GetAllBillsByDatesOutputPort {
    List<BillResponse> getAllBillsByDates(LocalDate startDate, LocalDate endDate);
}
