package com.eatsleep.reports.infrastructure.inputports;

import com.eatsleep.reports.application.allearningslocationdateusecase.TopsBillDescriptionResponse;
import java.time.LocalDate;
import java.util.List;

public interface GetBillsDescriptionByClientByDatesInputPort {
List<TopsBillDescriptionResponse> getBillsDescriptionByClientByDates(String idClient, LocalDate startDate, LocalDate endDate);
}
