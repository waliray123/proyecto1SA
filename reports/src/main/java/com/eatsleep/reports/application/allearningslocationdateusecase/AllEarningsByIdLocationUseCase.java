package com.eatsleep.reports.application.allearningslocationdateusecase;

import com.eatsleep.reports.common.UseCase;
import com.eatsleep.reports.infrastructure.inputports.GetBillsDescriptionByIdLocationByDatesInputPort;
import com.eatsleep.reports.infrastructure.outputadapters.restapi.ReportsRestApiOutputAdapter;
import jakarta.transaction.Transactional;
import java.time.LocalDate;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;

@UseCase
@Transactional
public class AllEarningsByIdLocationUseCase implements GetBillsDescriptionByIdLocationByDatesInputPort {
    
    private ReportsRestApiOutputAdapter reportsRestApiOutputAdapter;

    @Autowired
    public AllEarningsByIdLocationUseCase(ReportsRestApiOutputAdapter reportsRestApiOutputAdapter) {
        this.reportsRestApiOutputAdapter = reportsRestApiOutputAdapter;
    }

    @Override
    public List<TopsBillDescriptionResponse> getBillsDescriptionByIdLocationByDates(String idLocation, LocalDate startDate, LocalDate endDate) {
        return this.reportsRestApiOutputAdapter.getBillsDescriptionByIdLocationByDates(idLocation, startDate, endDate);
    }


    
}
