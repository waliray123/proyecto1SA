package com.eatsleep.reports.infrastructure.inputports;

import com.eatsleep.reports.application.allearningslocationdateusecase.TopsBillDescriptionResponse;
import java.time.LocalDate;
import java.util.List;

public interface GetBillsDescriptionByClientIdLocationByDatesInputPort {
    List<TopsBillDescriptionResponse> getBillsDescriptionByClientIdLocationByDates(
        String idClient, String idLocation, LocalDate startDate, LocalDate endDate);
}
