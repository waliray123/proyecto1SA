package com.eatsleep.reports.infrastructure.outputports.restapi;

import com.eatsleep.reports.application.allearningslocationdateusecase.TopsBillDescriptionResponse;
import java.time.LocalDate;
import java.util.List;

public interface GetBillsDescriptionByClientByDatesOutputPort {
List<TopsBillDescriptionResponse> getBillsDescriptionByClientByDates(String idClient, LocalDate startDate, LocalDate endDate);
}
