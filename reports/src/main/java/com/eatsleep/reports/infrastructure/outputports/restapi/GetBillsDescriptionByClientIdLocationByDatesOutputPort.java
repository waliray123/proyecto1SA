package com.eatsleep.reports.infrastructure.outputports.restapi;

import com.eatsleep.reports.application.allearningslocationdateusecase.TopsBillDescriptionResponse;
import java.time.LocalDate;
import java.util.List;

public interface GetBillsDescriptionByClientIdLocationByDatesOutputPort {
    List<TopsBillDescriptionResponse> getBillsDescriptionByClientIdLocationByDates(
        String idClient, String idLocation, LocalDate startDate, LocalDate endDate);
}
