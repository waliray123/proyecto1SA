package com.eatsleep.reports.infrastructure.inputports;

import com.eatsleep.reports.application.allearningscostsusecase.EarningResponse;
import java.time.LocalDate;
import java.util.List;

public interface GetEarningsCostsInputPort {
    List<EarningResponse> getEarningsCostsByDates(LocalDate startDate, LocalDate endDate);
}
