package com.eatsleep.reports.infrastructure.inputports;

import com.eatsleep.reports.application.allearningslocationdateusecase.TopsBillDescriptionResponse;
import java.time.LocalDate;
import java.util.List;

public interface GetBillsDescriptionByIdLocationByDatesInputPort {
    List<TopsBillDescriptionResponse> getBillsDescriptionByIdLocationByDates(String idLocation, LocalDate startDate, LocalDate endDate);
}
